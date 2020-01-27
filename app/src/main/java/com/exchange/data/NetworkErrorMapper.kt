package com.exchange.data

import com.exchange.domain.model.GenericException
import com.exchange.domain.model.NetworkException
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

fun Throwable.toNetworkError(): Exception = when (this) {
    is HttpException -> {
        val errorBody = response()?.errorBody()
        if (errorBody != null) {
            when (code()) {
                HTTP_FORBIDDEN -> NetworkException.Forbidden(this)
                HTTP_UNAUTHORIZED -> NetworkException.Unauthorized(this)
                else -> NetworkException.Http(code(), response()?.message(), this)
            }
        } else NetworkException.Http(code(), response()?.message(), this)
    }
    is IOException -> NetworkException.Connection(this)
    else -> GenericException(cause = this)
}
