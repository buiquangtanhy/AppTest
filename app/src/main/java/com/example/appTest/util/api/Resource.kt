package com.example.appTest.util.api

import com.example.appTest.util.ErrorCode


sealed class Resource<out T> {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Success(data)

        fun <T> error(data: T? = null, code: ErrorCode): Resource<T> =
            Error(data, code)


        fun <T> loading(data: T? = null): Resource<T> =
            Loading(data)
    }
    data class Loading<T>(val cachedData: T?) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val cachedData: T?, val code: ErrorCode) : Resource<T>()
}