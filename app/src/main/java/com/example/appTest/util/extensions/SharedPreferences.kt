package com.example.appTest.util.extensions

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.appTest.util.livedata.*

/**
 * Provide **Int** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.intLiveData(key: String, defValue: Int): MutableLiveData<Int> =
    SharedPreferenceIntLiveData(this, key, defValue)

/**
 * Provide **String** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.stringLiveData(
    key: String,
    defValue: String
): MutableLiveData<String> = SharedPreferenceStringLiveData(this, key, defValue)

/**
 * Provide **Nullable String** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.stringNullableLiveData(
    key: String,
    defValue: String?
): MutableLiveData<String?> = SharedPreferenceStringNullableLiveData(this, key, defValue)

/**
 * Provide **Boolean** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.booleanLiveData(
    key: String,
    defValue: Boolean
): MutableLiveData<Boolean> =
    SharedPreferenceBooleanLiveData(this, key, defValue)

/**
 * Provide **Float** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.floatLiveData(key: String, defValue: Float): MutableLiveData<Float> =
    SharedPreferenceFloatLiveData(this, key, defValue)

/**
 * Provide **Long** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.longLiveData(key: String, defValue: Long): MutableLiveData<Long> =
    SharedPreferenceLongLiveData(this, key, defValue)

/**
 * Provide **String Set** value of [SharedPreferences] under [MutableLiveData]
 */
fun SharedPreferences.stringSetLiveData(
    key: String,
    defValue: Set<String>
): MutableLiveData<Set<String>> =
    SharedPreferenceStringSetLiveData(this, key, defValue)

/**
 * Provide **Enum** value of [SharedPreferences] under [MutableLiveData]
 */
inline fun <reified T : Enum<T>> SharedPreferences.enumLiveData(
    key: String,
    defValue: T
): MutableLiveData<T> =
    SharedPreferenceEnumLiveData(this, key, defValue, T::class)
