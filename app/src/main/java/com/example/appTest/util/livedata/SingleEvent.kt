package com.example.appTest.util.livedata

/**
 * Wrapper for type T indicate value type T is handle only once.
 *
 * Used in LiveData for single event like: Toast, SnackBar, Open Screen,...etc
 *
 * - In ViewModel:
 * ```
 * private val statusMessage = MutableLiveData<Event<String>>()
 * val message : LiveData<Event<String>>
 *      get() = statusMessage
 *
 * statusMessage.value = Event("User Updated Successfully")
 * ```
 * - In Activity:
 * ```
 * yourViewModel.message.observe(this, Observer {
 *      it.getContentIfNotHandled()?.let {
 *          Toast.makeText(this, it, Toast.LENGTH_LONG).show()
 *      }
 * })
 * ```
 */
class SingleEvent<out T>(private val content: T) {

    companion object {
        fun <T> createOrNull(data: T?): SingleEvent<T>? = data?.let { SingleEvent(it) }
    }

    var hasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}