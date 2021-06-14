package com.example.appTest.repository

import com.example.appTest.entity.Item
import com.example.appTest.repository.remote.DataResponse
import com.example.appTest.util.api.Resource
import kotlinx.coroutines.flow.Flow

interface Repo {
    // SETUP HEADER ------------------------------------------------------------------------------------

    fun setHeaderRequest(headers: Map<String, String>)

//    fun getItems(force: Boolean = false): Flow<Resource<DataResponse<List<ItemEntity>>>>
    fun getItems(force: Boolean = false): Flow<Resource<List<Item>>>

}