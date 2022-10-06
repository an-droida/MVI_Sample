package com.androida.currencyexchanger.presentation.currency

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.fragment.base.bottomSheet.BaseBottomSheetFragment
import com.androida.currencyexchanger.core.fragment.utils.ViewBindingFactory
import com.androida.currencyexchanger.databinding.FragmentCurrencyRatesBinding
import com.androida.currencyexchanger.presentation.currency.list.CurrencyRatesAdapter
import com.androida.currencyexchanger.presentation.dashboard.dialog.openCurrencyConvertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class CurrencyRatesFragment :
    BaseBottomSheetFragment<FragmentCurrencyRatesBinding, CurrencyRatesViewAction, CurrencyRatesViewState, CurrencyRatesViewModel, CurrencyRatesViewData>() {
    override val viewModelClass: KClass<CurrencyRatesViewModel> = CurrencyRatesViewModel::class
    override val viewBindingFactory: ViewBindingFactory<FragmentCurrencyRatesBinding> =
        FragmentCurrencyRatesBinding::inflate

    override val heightPercent: Float = 0.95F
    override val peekHeightPercent: Float = 0.95F

    private lateinit var adapter: CurrencyRatesAdapter

    override fun onDraw(
        view: View,
        lastState: CurrencyRatesViewData?,
        viewBinding: FragmentCurrencyRatesBinding
    ) {
        super.onDraw(view, lastState, viewBinding)

        adapter = CurrencyRatesAdapter {
            postAction(CurrencyRatesViewAction.OnCurrencySelected(it))
            dismissAllowingStateLoss()
        }
        viewBinding.rvCurrency.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvCurrency.adapter = adapter
        adapter.updateData(CurrencyRates.values().toList())

        viewBinding.etSearch.doAfterTextChanged {
            postAction(CurrencyRatesViewAction.OnListFiltering(it?.toString() ?: ""))
        }
    }

    override fun reflectState(
        state: CurrencyRatesViewState,
        viewBinding: FragmentCurrencyRatesBinding
    ) {
        super.reflectState(state, viewBinding)
        when (state) {
            is CurrencyRatesViewState.OnCurrencyReceived -> {
                adapter.updateData(state.rates)
            }
            is CurrencyRatesViewState.OnListFiltered -> {
                adapter.updateData(state.filteredList)
            }
            else -> Unit
        }
    }

}