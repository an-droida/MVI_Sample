package com.androida.currencyexchanger.domain.di

import android.content.Context
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.data.remote.CurrencyService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideJson(): Json = Json(builderAction = {
        ignoreUnknownKeys = true
        isLenient = true
    })

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideSerializer(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideClient(
        @Named("logging") logger: Interceptor,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logger)
        .build()

    @Provides
    @Singleton
    fun getBaseUrl(@ApplicationContext context: Context): String =
        context.getString(R.string.api_address)

    @Provides
    @Singleton
    @Named("logging")
    fun getLogger(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        converter: Converter.Factory,
        client: OkHttpClient,
        apiAddress: String
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(apiAddress)
        .client(client)
        .addConverterFactory(converter)
        .build()


    @Provides
    @Singleton
    fun provideCurrencyService(
        retrofit: Retrofit
    ): CurrencyService = retrofit.create(CurrencyService::class.java)

}
