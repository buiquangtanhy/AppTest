package com.example.appTest.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.example.appTest.util.extensions.myActivityViewModels
import com.example.appTest.util.extensions.myViewModels
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

// BASE CLASSES ------------------------------------------------------------------------------------

/**
 * Base class for all activities
 * @param VM [ViewModel] class of [Activity]
 */
abstract class BaseActivity<D : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    protected val viewModel by myViewModels(secondParameterizedClass())
    protected lateinit var binding: D
}

/**
 * Base class for all fragments
 * @param VM [ViewModel] class of [Fragment]
 *
 */
abstract class BaseFragment<D : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected val viewModel by myViewModels(secondParameterizedClass())
    protected lateinit var binding: D
}

/**
 * Base class for fragment
 * @param VM [ViewModel] class of [Fragment]
 * @param AVM [ViewModel] class of [Activity]
 */
abstract class BaseFragment2<D : ViewDataBinding, VM : ViewModel, AVM : ViewModel> :
    BaseFragment<D, VM>() {

    val activityViewModel by myActivityViewModels(thirdParameterizedClass())
}

/**
 * Base class for child fragment that want have parent fragment viewmodel
 * @param D Data binding class
 * @param VM [ViewModel] class of child fragment
 * @param PVM [ViewModel] class of parent fragment
 */
abstract class BaseChildFragment<D : ViewDataBinding, VM : ViewModel, PVM : ViewModel> :
    Fragment() {

    protected lateinit var binding: D
    protected val viewModel by myViewModels(secondParameterizedClass())
    protected val parentViewModel by myViewModels(
        kClass = thirdParameterizedClass(),
        ownerProducer = { requireParentFragment() },
    )
}

/**
 * Base class for child fragment that want have parent fragment viewmodel
 * @param D Data binding class
 * @param VM [ViewModel] class of child fragment
 * @param PVM [ViewModel] class of parent fragment
 * @param AVM [ViewModel] class of activity
 */
abstract class BaseChildFragment2<D : ViewDataBinding, VM : ViewModel, PVM : ViewModel, AVM : ViewModel> :
    BaseChildFragment<D, VM, PVM>() {

    @Suppress("unused")
    protected val activityViewModel by myActivityViewModels(fourthParameterizedClass())
}

/**
 * Base class for dialog fragment
 * @param D Data binding class
 * @param VM [ViewModel] class of [DialogFragment]
 */
abstract class BaseDialogFragment<D : ViewDataBinding, VM : ViewModel> : DialogFragment() {

    protected lateinit var binding: D
    protected val viewModel by myViewModels(secondParameterizedClass())

}

/**
 * Base class for dialog fragment
 * @param D Data binding class
 * @param VM [ViewModel] class of [DialogFragment]
 * @param AVM [ViewModel] class of [Activity]
 */
abstract class ActivityBaseDialogFragment<D : ViewDataBinding, VM : ViewModel, AVM : ViewModel> :
    BaseDialogFragment<D, VM>() {

    protected val activityViewModel by myActivityViewModels(thirdParameterizedClass())
}

/**
 * Base class for [ViewModel]
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver

// EXTENSION ---------------------------------------------------------------------------------------

/**
 * Get [KClass] of parameterizedType argument [T]
 */
private fun <T : ViewModel> BaseActivity<*, T>.secondParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[1] as Class<T>).kotlin
    }
}

/**
 * Get [KClass] of parameterizedType argument [T]
 */
private fun <T : ViewModel> BaseFragment<*, T>.secondParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[1] as Class<T>).kotlin
    }
}

/**
 * Get [KClass] of parameterizedType argument [T]
 */
fun <T : ViewModel> BaseFragment2<*, *, T>.thirdParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[2] as Class<T>).kotlin
    }
}

private fun <T : ViewModel> BaseChildFragment<*, T, *>.secondParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[1] as Class<T>).kotlin
    }
}

private fun <T : ViewModel> BaseChildFragment<*, *, T>.thirdParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[2] as Class<T>).kotlin
    }
}

private fun <T : ViewModel> BaseChildFragment2<*, *, *, T>.fourthParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[3] as Class<T>).kotlin
    }
}

private fun <T : ViewModel> BaseDialogFragment<*, T>.secondParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[1] as Class<T>).kotlin
    }
}

private fun <T : ViewModel> ActivityBaseDialogFragment<*, *, T>.thirdParameterizedClass(): KClass<T> {
    val superClass: Type? = javaClass.genericSuperclass
    return if (superClass == null) {
        throw RuntimeException("Call to parameterizedClass() but don't have generic super class")
    } else {
        @Suppress("UNCHECKED_CAST")
        ((superClass as ParameterizedType).actualTypeArguments[2] as Class<T>).kotlin
    }
}