package com.example.appTest.repository.remote


import com.example.appTest.entity.Item
import com.example.appTest.util.api.APIResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface RemoteService {

    @GET("albums/{page}/photos")
    fun getItems(@Path("page") page: Int): Flow<APIResponse<List<Item>>>
}