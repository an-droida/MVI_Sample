package com.androida.currencyexchanger.presentation.dashboard

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.extensions.log
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
                if (!etSell.text.isNullOrEmpty() && !etReceive.text.isNullOrEmpty()) {
                    postAction(DashboardViewAction.OnSubmitButtonClicked)
                }
            }
            etSell.observeInputAmount(etReceive, CurrencyPickerType.SELL, viewBinding)
            etReceive.observeInputAmount(etSell, CurrencyPickerType.RECEIVE, viewBinding)

            tvReceiveCurrency.onClick {
                postAction(DashboardViewAction.RegisterCurrencyPickerAction)
                postAction(DashboardViewAction.NavigateToCurrencyRates(CurrencyPickerType.RECEIVE))
            }
            tvSellCurrency.onClick {
                postAction(DashboardViewAction.RegisterCurrencyPickerAction)
                postAction(DashboardViewAction.NavigateToCurrencyRates(CurrencyPickerType.SELL))
            }
        }
    }


    private fun EditText.observeInputAmount(
        secondInput: EditText,
        pickerType: CurrencyPickerType,
        viewBinding: FragmentDashboardBinding
    ) {
        doAfterTextChanged {
            if (this.isFocused) {
                try {
                    if ((it.isNullOrEmpty() || it.toString().toDouble() == 0.0)) {
                        secondInput.setText("0")
                        viewBinding.btnSubmit.isEnabled = false
                    } else {
                        postAction(
                            DashboardViewAction.OnConvertAmountChanged(
                                amount = it.toString().toDouble(),
                                type = pickerType
                            )
                        )
                        viewBinding.btnSubmit.isEnabled = true
                    }
                } catch (e: java.lang.NumberFormatException) {
                    viewBinding.btnSubmit.isEnabled = false
                    e.log("NumberFormatException")
                }
            }
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
                    CurrencyPickerType.SELL -> {
                        viewBinding.tvSellCurrency.text = state.currencyRates.selection.currency
                    }
                    CurrencyPickerType.RECEIVE -> {
                        viewBinding.tvReceiveCurrency.text = state.currencyRates.selection.currency
                    }
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
            is DashboardViewState.NoEnoughBalanceErrorReceived -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_enough_balance_msg),
                    Toast.LENGTH_LONG
                ).show()
            }
            is DashboardViewState.OpenConfirmationDialog -> {
                openCurrencyConvertDialog(
                    currencyFrom = viewBinding.tvSellCurrency.text.toString(),
                    currencyTo = viewBinding.tvReceiveCurrency.text.toString(),
                    sell = viewBinding.etSell.text.toString().toDouble(),
                    receive = viewBinding.etReceive.text.toString().toDouble(),
                    state.convertCount
                ) { sell, receive ->
                    postAction(DashboardViewAction.OnSubmitBalanceClicked(sell, receive))
                }
            }
        }
    }
}