package com.androida.currencyexchanger.domain.repositories

import com.androida.currencyexchanger.data.models.remote.ConvertCurrencyResponse
import com.androida.currencyexchanger.data.models.remote.GetCurrencyResponse
import com.androida.currencyexchanger.data.remote.CurrencyService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override suspend fun getCurrency(baseCurrency: String): Response<GetCurrencyResponse> {
        return currencyService.getCurrency(baseCurrency)
    }

    override suspend fun convertCurrency(
        amount: String,
        from: String,
        to: String
    ): Response<ConvertCurrencyResponse> {
        return currencyService.convertCurrency(
            amount = amount, from = from, to = to
        )
    }

}