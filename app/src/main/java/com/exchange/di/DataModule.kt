package com.exchange.di

import com.exchange.BuildConfig
import com.exchange.data.ExchangeApi
import com.exchange.data.ExchangeRepositoryImpl
import com.exchange.domain.ExchangeRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindExchangeRepository(repository: ExchangeRepositoryImpl): ExchangeRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideExchangeApi(retrofit: Retrofit): ExchangeApi = retrofit.create(ExchangeApi::class.java)

        @Provides
        @Singleton
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.EXCHANGE_ENDPOINT)
            .addConverterFactory(
                Json(JsonConfiguration(strictMode = false)).asConverterFactory(MediaType.get("application/json"))
            )
            .build()

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
    }
}
