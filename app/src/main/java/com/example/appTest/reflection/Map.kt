package com.example.appTest.reflection

import kotlin.reflect.KParameter

/**
 * Filter [Map<KParameter : Any?>] that value != null || parameter key is mark with nullable || parameter key is optional
 */
fun <K : KParameter, V> Map<K, V>.filterValueNonNullOrKeyIsNullable(): Map<K, V> =
    filter { entry -> entry.value != null || entry.key.type.isMarkedNullable || entry.key.isOptional }