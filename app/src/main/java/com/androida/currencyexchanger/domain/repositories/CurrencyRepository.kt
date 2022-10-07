package com.androida.currencyexchanger.domain.repositories

import com.androida.currencyexchanger.data.models.remote.ConvertCurrencyResponse
import com.androida.currencyexchanger.data.models.remote.GetCurrencyResponse
import retrofit2.Response

interface CurrencyRepository {

    suspend fun getCurrency(baseCurrency: String): Response<GetCurrencyResponse>

    suspend fun convertCurrency(
        amount: String,
        from: String,
        to: String
    ): Response<ConvertCurrencyResponse>
}