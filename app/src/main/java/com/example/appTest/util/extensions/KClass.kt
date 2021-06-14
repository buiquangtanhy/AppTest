package com.example.appTest.util.extensions


import kotlin.reflect.KClass

/**
 * Get [Enum] values of [KClass] [Enum] type
 * @return [Array] of [Enum] values
 */
fun <T : Enum<T>> KClass<T>.enumValues(): Array<out T>? = java.enumConstants