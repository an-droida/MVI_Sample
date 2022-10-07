package com.androida.currencyexchanger.presentation.currency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.domain.useCases.FilterCurrencyListUseCase
import com.androida.currencyexchanger.domain.useCases.LoadCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyRatesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loadCurrenciesUseCase: LoadCurrenciesUseCase,
    private val filterCurrencyListUseCase: FilterCurrencyListUseCase
) : BaseViewModel<CurrencyRatesViewAction, CurrencyRatesViewState, CurrencyRatesViewData>() {
    override var viewStateData: CurrencyRatesViewData = CurrencyRatesViewData()

    override fun onInitialBind() {
        super.onInitialBind()

        val argument = savedStateHandle.get<CurrencyPickerType>(savedStateHandle.keys().first())
        if (argument != null) {
            postState(CurrencyRatesViewState.OnPickerTypeReceived(argument))
        }
        getCurrencyRates()
    }

    override fun onActionReceived(action: CurrencyRatesViewAction) {
        super.onActionReceived(action)
        when (action) {
            is CurrencyRatesViewAction.OnListFiltering -> {
                filterList(action.filteredText)
            }
            else -> Unit
        }
    }

    private fun getCurrencyRates() {
        loadCurrenciesUseCase(
            scope = viewModelScope,
            withLoader = true,
            params = Unit
        ) { currencyRates ->
            if (currencyRates.isNotEmpty()) {
                postState(CurrencyRatesViewState.OnCurrencyReceived(currencyRates))
            }
        }
    }

    private fun filterList(filteredText: String?) {
        if (filteredText == null) return
        val filteredList =
            filterCurrencyListUseCase(filteredText, currencies = viewStateData.currency)
        postState(CurrencyRatesViewState.OnListFiltered(filteredList))
    }
}