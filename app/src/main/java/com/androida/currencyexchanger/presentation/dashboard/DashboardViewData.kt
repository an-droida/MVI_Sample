package com.androida.currencyexchanger.presentation.dashboard

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.data.models.local.MyBalanceModel

data class DashboardViewData(
    val myBalance: List<MyBalanceModel>? = null,
    val amount: Double? = null,
    val type: CurrencyPickerType = CurrencyPickerType.SELL,

    val baseCurrencyRate: String = "0.0",
    val baseCurrencyFrom: String = CurrencyRates.EUR.currency,
    val baseCurrencyTo: String = CurrencyRates.USD.currency,
)