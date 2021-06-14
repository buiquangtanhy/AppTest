package com.example.appTest.util.extensions


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
fun <T> MutableStateFlow<T?>.valueNotDistinct(newValue: T) {
    value = null
    value = newValue
}
