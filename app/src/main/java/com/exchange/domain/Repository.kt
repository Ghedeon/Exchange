package com.exchange.domain

import android.util.Log
import com.exchange.data.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface Repository {

    suspend fun <T> safeCall(body: suspend () -> T): Result<T> = try {
        Result.success(body())
    } catch (error: Throwable) {
        Log.e("Repository", "Request failed", error)
        Result.failure(error.toNetworkError())
    }

    suspend fun <T> safeIOCall(body: suspend () -> T) = withContext(Dispatchers.IO) { safeCall(body) }
}
