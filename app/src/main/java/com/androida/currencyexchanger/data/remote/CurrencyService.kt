package com.androida.currencyexchanger.data.remote

import com.androida.currencyexchanger.BuildConfig
import com.androida.currencyexchanger.data.models.remote.ConvertCurrencyResponse
import com.androida.currencyexchanger.data.models.remote.GetCurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyService {

    private val apiKey: String
        get() = BuildConfig.API_ACCESS_KEY

    @GET("latest")
    suspend fun getCurrency(
        @Query("base") base: String,
        @Header("apikey") accessKey: String = apiKey,
    ): Response<GetCurrencyResponse>

    @GET("convert")
    suspend fun convertCurrency(
        @Query("amount") amount: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Header("apikey") accessKey: String = apiKey,
    ): Response<ConvertCurrencyResponse>


}