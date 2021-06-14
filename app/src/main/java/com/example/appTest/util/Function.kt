package com.example.appTest.util

import timber.log.Timber
import java.util.*

// TRY CATCH ---------------------------------------------------------------------------------------

/**
 * Convenience function used to run a [block] of code that can throw [Exception]
 *
 * When [Exception] was threw, caller only want to log that [Exception] and return [default]
 */
fun <T : Any> tryCatchLog(default: T, block: () -> T): T =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

/**
 * Like [tryCatchLog] but can use with nullable value
 */
fun <T : Any> tryCatchLogNullable(default: T?, block: () -> T?): T? =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

suspend fun <T> tryCatchLogSuspendNullable(default: T?, block: suspend () -> T?): T? =
    try {
        block()
    } catch (cause: Throwable) {
        Timber.e(cause)
        default
    }

// TIME --------------------------------------------------------------------------------------------

fun now() = Date().time