package com.emericoapp.gituser.utils

import kotlinx.coroutines.delay
import android.util.Log
import java.io.IOException

/**
 * this is the class for Exponential backoff API call. if some API call get IOException
 */
suspend fun <T> retryIO(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100,
    maxDelay: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T): T
{
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            Log.i("viewRetry","exe: ${e.message}")
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block()
}


