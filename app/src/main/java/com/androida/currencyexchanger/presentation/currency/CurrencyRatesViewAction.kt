package com.androida.currencyexchanger.presentation.currency

import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.ObservableActionTypes
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.custom.others.CurrencySelection
import com.androida.currencyexchanger.core.fragment.action.BaseAction

sealed class CurrencyRatesViewAction : BaseAction<CurrencyRatesViewData> {

    class OnCurrencySelected(
        private val currencyRate: CurrencyRates,
        override var data: CurrencySelection? = null,
        override val type: ObservableActionTypes = ObservableActionTypes.CURRENCY_SELECTED
    ) : CurrencyRatesViewAction(), Command.ObservableFragmentAction<CurrencySelection> {
        override fun stateReducer(previousData: CurrencyRatesViewData): CurrencyRatesViewData {
            if (previousData.pickerTypes != null)
                data = CurrencySelection(
                    pickerType = previousData.pickerTypes,
                    selection = currencyRate
                )
            return previousData
        }
    }

    class OnListFiltering(val filteredText: String?) : CurrencyRatesViewAction()

}