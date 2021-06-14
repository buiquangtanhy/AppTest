package com.example.appTest.util.extensions


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

@Suppress("unused")
inline fun <X, Y> LiveData<X>.mapNotNull(crossinline transform: (X?) -> Y?): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { x ->
        val y = transform(x)
        y?.let {
            result.value = y
        }
    }
    return result
}

fun <X> LiveData<X?>.filterNotNull(): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        x?.let {
            result.value = x
        }
    }
    return result
}