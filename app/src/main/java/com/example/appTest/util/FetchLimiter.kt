package com.example.appTest.util

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 */
class FetchLimiter<T>(timeout: Int, timeUnit: TimeUnit) {
    private val timestamps = mutableMapOf<T, Long>()
    private val timeout = timeUnit.toMillis(timeout.toLong())
    private val mutex = Mutex()

    suspend fun shouldFetch(key: T): Boolean =
        mutex.withLock {
            val lastFetched = timestamps[key]
            val now = now()

            when {
                lastFetched == null -> {
                    timestamps[key] = now
                    true
                }

                now - lastFetched > timeout -> {
                    timestamps[key] = now
                    true
                }

                else -> false
            }
        }

    suspend fun reset(key: T): Unit =
        mutex.withLock {
            timestamps.remove(key)
        }
}