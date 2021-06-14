package com.its.food.delivery.util.reflection

import com.example.appTest.reflection.filterValueNonNullOrKeyIsNullable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.memberProperties

/**
 * Create an instance of class [T]
 *
 * @param T Class need create instance
 * @param paramMap Contain [parameterName: value] pair
 * @param constructor Constructor to create [T]
 * @return Instance of [T] class
 */
suspend inline fun <reified T : Any> createInstanceClass(
    paramMap: Map<String, *>,
    constructor: KFunction<T>
): T = coroutineScope {
    with(constructor) {
        callSuspendBy(
            args = parameters.associateWith { parameter ->
                ensureActive()
                val key = parameter.name
                when {
                    paramMap.containsKey(key) -> paramMap[key]
                    else -> throw IllegalArgumentException("${T::class.simpleName} have $key field that we haven't catch in reflection")
                }
            }.filterValueNonNullOrKeyIsNullable()
        )
    }
}

/**
 * Make a [Map<String: Any?>] from member properties of instance of [T] class
 */
inline fun <reified T : Any> T.memberPropertiesToMap(): MutableMap<String, Any?> =
    T::class.memberProperties.associateBy { it.name }.mapValues { it.value.get(this) }
        .toMutableMap()

/**
 * Transform [List<T>] -> [List<R>]
 *
 * @param transform Transform item [T] to item [R]
 */
suspend fun <T : Any, R : Any> List<T>.simpleTransform(transform: suspend (T) -> R): List<R> {
    val result: MutableList<R> = ArrayList(this.size)
    for (item in this) {
        result += transform(item)
    }
    return result
}