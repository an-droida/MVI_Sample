package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.activity.handlers.checkResponseWithData
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.custom.mappers.toRateAmount
import com.androida.currencyexchanger.domain.repositories.CurrencyRepository
import javax.inject.Inject

class LoadCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseUseCase<List<CurrencyRates>, Unit>() {

    override suspend fun run(params: Unit): List<CurrencyRates> {
        val response =
            currencyRepository.getCurrency(CurrencyRates.EUR.currency).checkResponseWithData()
        if (response.success == true && response.rates != null) {
            val currencyRates = CurrencyRates.values().toList()
            currencyRates.forEach { it.rate = it.toRateAmount(response.rates) }
            return currencyRates
        }
        return emptyList()
    }
}