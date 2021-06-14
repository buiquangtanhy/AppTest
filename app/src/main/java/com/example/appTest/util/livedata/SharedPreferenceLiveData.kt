package com.example.appTest.util.livedata

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.example.appTest.util.extensions.enumValues
import kotlin.reflect.KClass

/**
 * [SharedPreferences] with [MutableLiveData] to provide stream of SharedPreference data
 * @param T Type of data to save in [SharedPreferences]
 * @param sharedPrefs [SharedPreferences]
 * @param key Key to get value from [sharedPrefs]
 * @param defValue Default value for [key] if can't not found in [sharedPrefs]
 */
abstract class SharedPreferenceLiveData<T>(
    protected val sharedPrefs: SharedPreferences,
    private val key: String,
    private val defValue: T
) : MutableLiveData<T>() {

    protected abstract fun getValueFromPreferences(key: String, defValue: T): T

    protected abstract fun setValueFromPreferences(key: String, value: T)

    override fun onActive() {
        super.onActive()
        super.setValue(getValueFromPreferences(key, defValue))
    }

    override fun setValue(value: T) {
        setValueFromPreferences(key, value)
        super.setValue(value)
    }

    // TODO: Test this function on multi thread
    /**
     * SharedPreference thread-safe by default, so don't need addition code to synchronized
     */
    override fun postValue(value: T) {
        setValueFromPreferences(key, value)
        super.postValue(value)
    }
}

/**
 * SharedPreference LiveData class for **Int**
 */
class SharedPreferenceIntLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Int) :
    SharedPreferenceLiveData<Int>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Int) =
        sharedPrefs.getInt(key, defValue)

    override fun setValueFromPreferences(key: String, value: Int) =
        sharedPrefs.edit { putInt(key, value) }

}

/**
 * SharedPreference LiveData class for **String**
 */
class SharedPreferenceStringLiveData(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: String
) : SharedPreferenceLiveData<String>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: String) =
        sharedPrefs.getString(key, defValue)!!

    override fun setValueFromPreferences(key: String, value: String) =
        sharedPrefs.edit { putString(key, value) }

}

/**
 * SharedPreference LiveData class for **Nullable String**
 */
class SharedPreferenceStringNullableLiveData(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: String?
) : SharedPreferenceLiveData<String?>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: String?) =
        sharedPrefs.getString(key, defValue)

    override fun setValueFromPreferences(key: String, value: String?) =
        sharedPrefs.edit { putString(key, value) }

}

/**
 * SharedPreference LiveData class for **Boolean**
 */
class SharedPreferenceBooleanLiveData(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: Boolean
) : SharedPreferenceLiveData<Boolean>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Boolean) =
        sharedPrefs.getBoolean(key, defValue)

    override fun setValueFromPreferences(key: String, value: Boolean) =
        sharedPrefs.edit { putBoolean(key, value) }

}

/**
 * SharedPreference LiveData class for **Float**
 */
class SharedPreferenceFloatLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Float) :
    SharedPreferenceLiveData<Float>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Float) =
        sharedPrefs.getFloat(key, defValue)

    override fun setValueFromPreferences(key: String, value: Float) =
        sharedPrefs.edit { putFloat(key, value) }

}

/**
 * SharedPreference LiveData class for **Long**
 */
class SharedPreferenceLongLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Long) :
    SharedPreferenceLiveData<Long>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Long) =
        sharedPrefs.getLong(key, defValue)

    override fun setValueFromPreferences(key: String, value: Long) =
        sharedPrefs.edit { putLong(key, value) }

}

/**
 * SharedPreference LiveData class for **String Set**
 */
class SharedPreferenceStringSetLiveData(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: Set<String>
) : SharedPreferenceLiveData<Set<String>>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Set<String>): Set<String> =
        sharedPrefs.getStringSet(key, defValue)!!

    override fun setValueFromPreferences(key: String, value: Set<String>) =
        sharedPrefs.edit { putStringSet(key, value) }

}

/**
 * SharedPreference LiveData generic class for **Enum**
 */
class SharedPreferenceEnumLiveData<T : Enum<T>>(
    sharedPrefs: SharedPreferences,
    key: String,
    defValue: T,
    private val kClass: KClass<T>
) : SharedPreferenceLiveData<T>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: T): T =
        kClass.enumValues()?.get(sharedPrefs.getInt(key, defValue.ordinal))!!

    override fun setValueFromPreferences(key: String, value: T) =
        sharedPrefs.edit { putInt(key, value.ordinal) }

}
