package com.exchange.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

    @GET("latest")
    suspend fun getRates(@Query("base") baseCurrency: String): RatesResponse
}
