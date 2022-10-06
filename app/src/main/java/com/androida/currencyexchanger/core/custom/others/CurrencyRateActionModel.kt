package com.androida.currencyexchanger.core.custom.others

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates

class CurrencySelection(
    val selection: CurrencyRates,
    var pickerType: CurrencyPickerType
)