package com.androida.currencyexchanger.presentation.dashboard

import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.others.CurrencySelection
import com.androida.currencyexchanger.core.fragment.state.BaseViewState
import com.androida.currencyexchanger.data.models.local.MyBalanceModel

sealed class DashboardViewState : BaseViewState<DashboardViewData> {

    class OnMyBalanceReceived(
        val balance: List<MyBalanceModel>
    ) : DashboardViewState() {
        override fun stateReducer(previousData: DashboardViewData): DashboardViewData {
            return previousData.copy(myBalance = balance)
        }
    }
    object NoBalanceErrorReceived : DashboardViewState()

    class OpenConfirmationDialog(val convertCount: Int) : DashboardViewState()

    class OnConvertedCurrencyReceived(
        val type: CurrencyPickerType,
        val result: Double?
    ) : DashboardViewState()

    class OnCurrencyChanged(val currencyRates: CurrencySelection?) : DashboardViewState() {
        override fun stateReducer(previousData: DashboardViewData): DashboardViewData {
            return when (currencyRates?.pickerType) {
                CurrencyPickerType.SELL -> previousData.copy(
                    baseCurrencyFrom = currencyRates.selection.currency
                )
                CurrencyPickerType.RECEIVE -> previousData.copy(
                    baseCurrencyTo = currencyRates.selection.currency
                )
                else -> previousData
            }
        }
    }

    class OnNewCurrencyRateReceived(val result: Double?, val from: String, val to: String) :
        DashboardViewState() {
        override fun stateReducer(previousData: DashboardViewData): DashboardViewData {
            return previousData.copy(
                baseCurrencyRate = result.toString()
            )
        }
    }
}