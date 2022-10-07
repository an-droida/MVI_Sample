package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import javax.inject.Inject

class ConvertCurrencyLocallyUseCase @Inject constructor() {

    operator fun invoke(
        amount: Double,
        type: CurrencyPickerType,
        rate: String
    ): Double {
        var result = rate.toDouble().times(amount)
        result = when (type) {
            CurrencyPickerType.SELL -> result
            CurrencyPickerType.RECEIVE -> {
                (1.div(rate.toDouble())).times(amount)
            }
        }
        return result
    }
}