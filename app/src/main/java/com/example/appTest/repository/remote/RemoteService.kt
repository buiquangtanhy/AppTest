package com.example.appTest.repository.remote


import com.example.appTest.entity.Item
import com.example.appTest.util.api.APIResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface RemoteService {
//    @GET("getItems")
//    fun getItems(@HeaderMap headers: Map<String, String>): Flow<APIResponse<DataResponse<List<Item>>>>

    @GET("albums/1/photos")
    fun getItems(): Flow<APIResponse<List<Item>>>
}