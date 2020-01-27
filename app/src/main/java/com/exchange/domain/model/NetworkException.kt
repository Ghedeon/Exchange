package com.exchange.domain.model

sealed class NetworkException(override val cause: Throwable) : Exception(cause) {
    data class Http(val code: Int, override val message: String?, override val cause: Throwable) :
        NetworkException(cause)

    data class Forbidden(override val cause: Throwable) : NetworkException(cause)
    data class Unauthorized(override val cause: Throwable) : NetworkException(cause)
    data class Connection(override val cause: Throwable) : NetworkException(cause)
}

class GenericException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

