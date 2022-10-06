package com.androida.currencyexchanger.presentation.dashboard

import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.extensions.onClick
import com.androida.currencyexchanger.core.fragment.base.fragment.BaseFragment
import com.androida.currencyexchanger.core.fragment.utils.ViewBindingFactory
import com.androida.currencyexchanger.databinding.FragmentDashboardBinding
import com.androida.currencyexchanger.presentation.dashboard.dialog.openCurrencyConvertDialog
import com.androida.currencyexchanger.presentation.dashboard.list.MyBalanceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewAction, DashboardViewState, DashboardViewModel, DashboardViewData>() {

    override val viewModelClass = DashboardViewModel::class
    override val viewBindingFactory: ViewBindingFactory<FragmentDashboardBinding> =
        FragmentDashboardBinding::inflate

    private lateinit var adapter: MyBalanceAdapter

    override fun onDraw(
        view: View,
        lastState: DashboardViewData?,
        viewBinding: FragmentDashboardBinding
    ) {
        super.onDraw(view, lastState, viewBinding)

        with(viewBinding) {
            adapter = MyBalanceAdapter()
            rvBalance.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvBalance.adapter = adapter

            btnSubmit.onClick {
                if (tvSellCurrency.text.isNullOrEmpty()) {
                    openCurrencyConvertDialog(
                        currencyFrom = tvSellCurrency.text.toString(),
                        currencyTo = tvReceiveCurrency.text.toString(),
                        sell = etSell.text.toString(),
                        receive = etReceive.text.toString()
                    ) { sell, receive ->
                        postAction(DashboardViewAction.OnSubmitBalanceClicked(sell, receive))
                    }
                }
            }
        }

        viewBinding.etSell.doAfterTextChanged {
            if (viewBinding.etSell.isFocused) {
                if ((it.isNullOrEmpty() || it.toString().toDouble() == 0.0)) {
                    viewBinding.etReceive.setText("0")
                } else {
                    postAction(
                        DashboardViewAction.OnConvertAmountChanged(
                            amount = it.toString().toDouble(),
                            type = CurrencyPickerType.SELL
                        ),
                    )
                }
            }
        }
        viewBinding.etReceive.doAfterTextChanged {
            if (viewBinding.etReceive.isFocused) {
                if ((it.isNullOrEmpty() || it.toString().toDouble() == 0.0)) {
                    viewBinding.etSell.setText("0")
                } else {
                    postAction(
                        DashboardViewAction.OnConvertAmountChanged(
                            amount = it.toString().toDouble(),
                            type = CurrencyPickerType.RECEIVE
                        )
                    )
                }
            }
        }

        viewBinding.tvReceiveCurrency.onClick {
            postAction(DashboardViewAction.RegisterCurrencyPickerAction)
            postAction(DashboardViewAction.NavigateToCurrencyRates(CurrencyPickerType.RECEIVE))
        }

        viewBinding.tvSellCurrency.onClick {
            postAction(DashboardViewAction.RegisterCurrencyPickerAction)
            postAction(DashboardViewAction.NavigateToCurrencyRates(CurrencyPickerType.SELL))
        }

    }

    override fun reflectState(state: DashboardViewState, viewBinding: FragmentDashboardBinding) {
        super.reflectState(state, viewBinding)
        when (state) {
            is DashboardViewState.OnConvertedCurrencyReceived -> {
                val roundedResult = String.format("%.2f", state.result)
                when (state.type) {
                    CurrencyPickerType.SELL -> viewBinding.etReceive.setText(roundedResult)
                    CurrencyPickerType.RECEIVE -> viewBinding.etSell.setText(roundedResult)
                }
            }
            is DashboardViewState.OnCurrencyChanged -> {
                when (state.currencyRates?.pickerType) {
                    CurrencyPickerType.SELL -> viewBinding.tvSellCurrency.text =
                        state.currencyRates.selection.currency
                    CurrencyPickerType.RECEIVE -> viewBinding.tvReceiveCurrency.text =
                        state.currencyRates.selection.currency
                    else -> Unit
                }
                postAction(DashboardViewAction.FetchNewCurrencyExchange)
            }
            is DashboardViewState.OnNewCurrencyRateReceived -> {
                viewBinding.tvBaseCurrencyRate.text =
                    "1 ${state.from} = ${state.result} ${state.to}"
            }
            is DashboardViewState.OnMyBalanceReceived -> {
                adapter.updateData(state.balance)
            }
            is DashboardViewState.NoBalanceErrorReceived -> {
                Toast.makeText(
                    requireContext(),
                    "There is not enough money on balance",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}