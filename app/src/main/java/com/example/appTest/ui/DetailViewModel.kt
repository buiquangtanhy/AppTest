package com.example.appTest.ui

import android.content.Intent
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appTest.entity.Item
import com.example.appTest.util.BUNDLE_KEY
import com.example.appTest.util.ITEM_ENTITY_KEY
import com.example.appTest.util.extensions.valueNotDistinct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel

class DetailViewModel @Inject constructor() : BaseViewModel() {
    private val _item by lazy { MutableStateFlow<Item?>(null) }
    val item by lazy {
        _item.filterNotNull().asLiveData(viewModelScope.coroutineContext)
    }

    fun processIntentData(intent: Intent?) {
        val item = intent?.getBundleExtra(BUNDLE_KEY)?.get(ITEM_ENTITY_KEY) as Item
        if (item == null) {
            throw IllegalArgumentException("food must not be null")
        } else {
            this._item.valueNotDistinct(item)
        }
    }
}