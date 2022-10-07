package com.androida.currencyexchanger.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.androida.currencyexchanger.core.custom.consts.INITIAL_BALANCE
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import com.androida.currencyexchanger.domain.repositories.CurrencyPreferenceRepository
import com.androida.currencyexchanger.domain.repositories.MyBalanceRepository
import com.androida.currencyexchanger.domain.useCases.ConvertCurrencyLocallyUseCase
import com.androida.currencyexchanger.domain.useCases.FetchInitialExchangeCurrencyUseCase
import com.androida.currencyexchanger.domain.useCases.UpdateMyBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val myBalanceRepo: MyBalanceRepository,
    private val preferencesRepo: CurrencyPreferenceRepository,
    private val updateMyBalanceUseCase: UpdateMyBalanceUseCase,
    private val convertCurrencyLocallyUseCase: ConvertCurrencyLocallyUseCase,
    private val fetchInitialExchangeCurrencyUseCase: FetchInitialExchangeCurrencyUseCase,
) : BaseViewModel<DashboardViewAction, DashboardViewState, DashboardViewData>() {

    override var viewStateData: DashboardViewData = DashboardViewData()

    override fun onInitialBind() {
        super.onInitialBind()

        postAction(DashboardViewAction.RegisterCurrencyPickerAction)
        getMyBalance()
        fetchNewConvertCurrency()
    }

    private fun getMyBalance() {
        viewModelScope.launch {
            myBalanceRepo.getAllData().collect {
                if (it.isEmpty()) {
                    myBalanceRepo.saveOrUpdateData(
                        MyBalanceModel(
                            balance = INITIAL_BALANCE,
                            currency = CurrencyRates.EUR.currency,
                            currencyId = CurrencyRates.valueOf(CurrencyRates.EUR.currency).currencyId
                        )
                    )
                } else {
                    postState(DashboardViewState.OnMyBalanceReceived(it))
                }
            }
        }
    }

    override fun onActionReceived(action: DashboardViewAction) {
        super.onActionReceived(action)
        when (action) {
            is DashboardViewAction.OnConvertAmountChanged -> {
                onConvertAmountChanged(action.amount, action.type)
            }
            is DashboardViewAction.OnCurrencyPickerActionReceived -> {
                postState(DashboardViewState.OnCurrencyChanged(action.data))
            }
            is DashboardViewAction.FetchNewCurrencyExchange -> {
                fetchNewConvertCurrency()
            }
            is DashboardViewAction.OnSubmitBalanceClicked -> {
                updateBalance(action.sell, action.receive)
            }
            DashboardViewAction.OnSubmitButtonClicked -> {
                viewModelScope.launch {
                    val count = preferencesRepo.getConvertedCount()
                    postState(DashboardViewState.OpenConfirmationDialog(count))
                }
            }
            else -> Unit
        }
    }

    private fun updateBalance(sell: Double, receive: Double) {
        updateMyBalanceUseCase(
            scope = viewModelScope,
            withLoader = true,
            params = UpdateMyBalanceUseCase.Params(
                baseCurrencyFrom = viewStateData.baseCurrencyFrom,
                baseCurrencyTo = viewStateData.baseCurrencyTo,
                sell = sell,
                receive = receive
            )
        ) {
            if (!it) {
                postState(DashboardViewState.NoEnoughBalanceErrorReceived)
            }
        }
    }

    private fun onConvertAmountChanged(
        amount: Double = viewStateData.amount ?: 0.0,
        type: CurrencyPickerType = viewStateData.type,
        rate: String = viewStateData.baseCurrencyRate
    ) {
        val result = convertCurrencyLocallyUseCase(amount = amount, type = type, rate = rate)
        postState(
            DashboardViewState.OnConvertedCurrencyReceived(
                type = type,
                result = result
            )
        )
    }

    private fun fetchNewConvertCurrency() {
        fetchInitialExchangeCurrencyUseCase(
            scope = viewModelScope,
            withLoader = true,
            params = FetchInitialExchangeCurrencyUseCase.Params(
                baseCurrencyFrom = viewStateData.baseCurrencyFrom,
                baseCurrencyTo = viewStateData.baseCurrencyTo
            )
        ) { resultResponse ->
            if (resultResponse != null) {
                postState(
                    DashboardViewState.OnNewCurrencyRateReceived(
                        resultResponse.result,
                        viewStateData.baseCurrencyFrom,
                        viewStateData.baseCurrencyTo,
                    )
                )
                onConvertAmountChanged(rate = resultResponse.result?.toString() ?: "0.0")
            }
        }
    }
}