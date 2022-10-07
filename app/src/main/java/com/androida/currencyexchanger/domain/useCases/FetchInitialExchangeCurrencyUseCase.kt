package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.activity.handlers.checkResponseWithData
import com.androida.currencyexchanger.data.models.remote.ConvertCurrencyResponse
import com.androida.currencyexchanger.domain.repositories.CurrencyRepository
import javax.inject.Inject

class FetchInitialExchangeCurrencyUseCase @Inject constructor(
    private val currencyRepo: CurrencyRepository
) : BaseUseCase<ConvertCurrencyResponse?, FetchInitialExchangeCurrencyUseCase.Params>() {

    data class Params(
        val baseCurrencyFrom: String,
        val baseCurrencyTo: String,
    )

    override suspend fun run(params: Params): ConvertCurrencyResponse? {
        val response = currencyRepo.convertCurrency(
            amount = "1",
            from = params.baseCurrencyFrom,
            to = params.baseCurrencyTo
        ).checkResponseWithData()
        if (response.success == true) {
            return response
        }
        return null
    }
}