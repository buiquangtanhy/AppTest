package com.example.appTest.ui

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appTest.entity.Item
import com.example.appTest.repository.Repo
import com.example.appTest.util.extensions.valueNotDistinct
import com.example.appTest.util.livedata.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo) :
    BaseViewModel() {

    private val _itemsEntity by lazy { MutableStateFlow<List<Item>?>(null) }
    val itemEntity by lazy {
        _itemsEntity.mapNotNull { SingleEvent.createOrNull(it) }
            .asLiveData(viewModelScope.coroutineContext)
    }

//    fun setHeaderApi() {
//        val headerMap = mutableMapOf<String, String>()
//        headerMap[ACCESS_TOKEN] = setting.token
//        repo.setHeaderRequest(headerMap)
//    }
//
    fun setAlbums(items: List<Item>?) {
        _itemsEntity.valueNotDistinct(items)
    }

    fun getAlbums() = repo.getItems().asLiveData()
}