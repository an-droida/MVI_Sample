package com.androida.currencyexchanger.presentation.dashboard

import com.androida.currencyexchanger.core.custom.consts.INITIAL_BALANCE
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import com.androida.currencyexchanger.domain.repositories.CurrencyPreferenceRepository
import com.androida.currencyexchanger.domain.repositories.CurrencyRepository
import com.androida.currencyexchanger.domain.repositories.MyBalanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepository,
    private val myBalanceRepo: MyBalanceRepository,
    private val preferencesRepo: CurrencyPreferenceRepository
) : BaseViewModel<DashboardViewAction, DashboardViewState, DashboardViewData>() {

    override var viewStateData: DashboardViewData = DashboardViewData()

    override fun onInitialBind() {
        super.onInitialBind()

        postAction(DashboardViewAction.RegisterCurrencyPickerAction)
        getMyBalance()
        fetchNewConvertCurrency()
    }

    private fun getMyBalance() {
        execute {
            myBalanceRepo.getAllData().collect {
                if (it.isEmpty()) {
                    saveBalance(
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

    private fun saveBalance(balance: MyBalanceModel) {
        execute {
            myBalanceRepo.saveOrUpdateData(balance)
        }
    }

    override fun onActionReceived(action: DashboardViewAction) {
        super.onActionReceived(action)
        when (action) {
            is DashboardViewAction.OnConvertAmountChanged -> {
                onConvertAmountChanged(
                    action.amount,
                    action.type
                )
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
                execute {
                    val count = preferencesRepo.getConvertedCount()
                    postState(DashboardViewState.OpenConfirmationDialog(count))
                }
            }
            else -> Unit
        }
    }

    private fun updateBalance(sell: String, receive: String) {
        execute {
            val currentBalance = myBalanceRepo.findMyBalanceCurrency(viewStateData.baseCurrencyFrom)
            if (currentBalance != null && currentBalance.balance.toDouble() >= sell.toDouble()) {
                val amount = (currentBalance.balance.toDouble() - sell.toDouble())
                val roundedAmount = String.format("%.2f", amount)
                myBalanceRepo.exchangeBalance(
                    amount = roundedAmount,
                    currency = viewStateData.baseCurrencyFrom
                )
                val convertedBalance =
                    myBalanceRepo.findMyBalanceCurrency(viewStateData.baseCurrencyTo)
                if (convertedBalance != null) {
                    val newBalance = convertedBalance.balance.toDouble() + receive.toDouble()
                    val roundedNewBalance = String.format("%.2f", newBalance)
                    myBalanceRepo.saveOrUpdateData(
                        MyBalanceModel(
                            balance = roundedNewBalance,
                            currency = convertedBalance.currency,
                            currencyId = CurrencyRates.valueOf(convertedBalance.currency).currencyId
                        )
                    )
                } else {
                    myBalanceRepo.saveOrUpdateData(
                        MyBalanceModel(
                            balance = receive,
                            currency = viewStateData.baseCurrencyTo,
                            currencyId = CurrencyRates.valueOf(viewStateData.baseCurrencyTo).currencyId
                        )
                    )
                }
                val count = preferencesRepo.getConvertedCount()
                preferencesRepo.saveConvertedCount(count + 1)
            } else {
                postState(DashboardViewState.NoBalanceErrorReceived)
            }
        }
    }

    private fun onConvertAmountChanged(
        amount: Double = viewStateData.amount ?: 0.0,
        type: CurrencyPickerType = viewStateData.type,
        rate: String = viewStateData.baseCurrencyRate ?: "0.0"
    ) {
        var result = rate.toDouble().times(amount)
        result = when (type) {
            CurrencyPickerType.SELL -> result
            CurrencyPickerType.RECEIVE -> {
                (1.div(rate.toDouble())).times(amount)
            }
        }
        postState(
            DashboardViewState.OnConvertedCurrencyReceived(
                type = type,
                result = result
            )
        )
    }

    private fun fetchNewConvertCurrency() {
        execute(withLoader = true) {
            val response = currencyRepo.convertCurrency(
                amount = "1",
                from = viewStateData.baseCurrencyFrom,
                to = viewStateData.baseCurrencyTo
            ).checkResponseWithData()
            if (response.success == true) {
                postState(
                    DashboardViewState.OnNewCurrencyRateReceived(
                        response.result,
                        viewStateData.baseCurrencyFrom,
                        viewStateData.baseCurrencyTo,
                    )
                )
                onConvertAmountChanged(rate = response.result?.toString() ?: "0.0")
            }
        }
    }
}