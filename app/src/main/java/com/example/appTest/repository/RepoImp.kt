package com.example.appTest.repository


import com.example.appTest.di.IODispatcher
import com.example.appTest.entity.Item
import com.example.appTest.repository.remote.RemoteService
import com.example.appTest.ui.WorkoutInstance
import com.example.appTest.util.FetchLimiter
import com.example.appTest.util.api.Resource
import com.example.appTest.util.api.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class RepoImp @Inject constructor(
    private val remoteService: RemoteService,
    private val appScope: CoroutineScope,
    @IODispatcher
    private val dispatcher: CoroutineDispatcher,
    private val fetchLimiter: FetchLimiter<String>,
) : Repo {

    private var headerRequest = mapOf<String, String>()

    override fun setHeaderRequest(headers: Map<String, String>) {
        headerRequest = headers
    }

    override fun getItems(force: Boolean): Flow<Resource<List<Item>>> =
        networkBoundResource(fetchFromRemote = {
            remoteService.getItems(WorkoutInstance.getInstance().getPage())
        }, remoteResponse = {
            val dataResponse = it ?: ArrayList()
            flow {
                emit(dataResponse)
            }
        })
}