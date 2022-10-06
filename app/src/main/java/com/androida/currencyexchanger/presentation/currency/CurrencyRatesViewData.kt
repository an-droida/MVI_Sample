package com.androida.currencyexchanger.presentation.currency

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates

data class CurrencyRatesViewData(
    val currency: List<CurrencyRates> = CurrencyRates.values().toList(),
    val pickerTypes: CurrencyPickerType? = null,
)