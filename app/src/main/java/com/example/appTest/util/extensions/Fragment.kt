package com.example.appTest.util.extensions


import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

/**
 * Like [viewModels] extension, but don't use inline reified
 */
@MainThread
fun <VM : ViewModel> Fragment.myViewModels(
    kClass: KClass<VM>,
    ownerProducer: () -> ViewModelStoreOwner = { this },
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(kClass, { ownerProducer().viewModelStore }, factoryProducer)

/**
 * Like [activityViewModels] extension, but don't use inline reified
 */
@MainThread
fun <VM : ViewModel> Fragment.myActivityViewModels(
    kClass: KClass<VM>,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(kClass, { requireActivity().viewModelStore },
    factoryProducer ?: { requireActivity().defaultViewModelProviderFactory })