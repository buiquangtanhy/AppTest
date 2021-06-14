package com.example.appTest.util.retrofit.adapter

import com.example.appTest.util.api.APIResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type


class FlowCallAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Flow<APIResponse<T>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<T>): Flow<APIResponse<T>> = flow {

        val response = call.awaitResponse()
        emit(APIResponse.create(response))

    }.catch { error ->
        emit(APIResponse.create(error))
    }
}