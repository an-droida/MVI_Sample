package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import java.util.*
import javax.inject.Inject

class FilterCurrencyListUseCase @Inject constructor() {

    operator fun invoke(
        filteredText: String,
        currencies: List<CurrencyRates>,
    ): List<CurrencyRates> {

        return currencies.filter {
            it.name.uppercase(Locale.getDefault())
                .contains(filteredText.uppercase(Locale.getDefault())) || it.currency.contains(
                filteredText
            )
        }
    }
}