package com.androida.currencyexchanger.presentation.currency

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.fragment.state.BaseViewState

sealed class CurrencyRatesViewState : BaseViewState<CurrencyRatesViewData> {

    class OnCurrencyReceived(val rates: List<CurrencyRates>) : CurrencyRatesViewState() {
        override fun stateReducer(previousData: CurrencyRatesViewData): CurrencyRatesViewData {
            return previousData.copy(currency = rates)
        }
    }

    class OnPickerTypeReceived(val pickerTypes: CurrencyPickerType) : CurrencyRatesViewState() {
        override fun stateReducer(previousData: CurrencyRatesViewData): CurrencyRatesViewData {
            return previousData.copy(pickerTypes = pickerTypes)
        }
    }

    class OnListFiltered(val filteredList: List<CurrencyRates>) : CurrencyRatesViewState()

}