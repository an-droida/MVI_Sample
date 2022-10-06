package com.androida.currencyexchanger.presentation.dashboard

import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.CommandResult
import com.androida.currencyexchanger.core.activity.commands.ObservableActionTypes
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.others.CurrencySelection
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions

sealed class DashboardViewAction : BaseAction<DashboardViewData> {

    object FetchNewCurrencyExchange : DashboardViewAction()

    class OnConvertAmountChanged(
        val amount: Double,
        val type: CurrencyPickerType
    ) : DashboardViewAction() {
        override fun stateReducer(previousData: DashboardViewData): DashboardViewData {
            return previousData.copy(amount = amount, type = type)
        }
    }

    class NavigateToCurrencyRates(
        private val pickerType: CurrencyPickerType,
        override val option: NavigationOptions = NavigationOptions.CurrencyRatesFragmentOption(
            pickerType
        )
    ) : DashboardViewAction(), Command.Navigation


    object RegisterCurrencyPickerAction : DashboardViewAction(),
        Command.RegisterFragmentAction<CurrencySelection> {
        override fun getCommandResult(): CommandResult.ObservableFragmentAction<CurrencySelection> =
            OnCurrencyPickerActionReceived()
    }

    class OnCurrencyPickerActionReceived(
        override val fragmentTag: String = NavigationOptions.DashboardFragmentOption().fragmentTag,
        override var data: CurrencySelection? = null,
        override val type: ObservableActionTypes = ObservableActionTypes.CURRENCY_SELECTED,
        override val viewModelTag: String = DashboardViewModel::class.java.simpleName
    ) : DashboardViewAction(), CommandResult.ObservableFragmentAction<CurrencySelection> {
        override fun stateReducer(previousData: DashboardViewData): DashboardViewData {
            return when (data?.pickerType) {
                CurrencyPickerType.SELL -> previousData.copy(
                    baseCurrencyFrom = data?.selection?.currency ?: ""
                )
                CurrencyPickerType.RECEIVE -> previousData.copy(
                    baseCurrencyTo = data?.selection?.currency ?: ""
                )
                else -> previousData
            }
        }
    }

    class OnSubmitBalanceClicked(val sell: String,val receive: String) : DashboardViewAction()
    object OnSubmitButtonClicked : DashboardViewAction()
}