package com.androida.currencyexchanger.presentation.currency

import androidx.lifecycle.SavedStateHandle
import com.androida.currencyexchanger.core.custom.enums.CurrencyPickerType
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.custom.mappers.toRateAmount
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.domain.repositories.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyRatesViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel<CurrencyRatesViewAction, CurrencyRatesViewState, CurrencyRatesViewData>() {
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
        execute(withLoader = true) {
            val response = currencyRepository.getCurrency("EUR").checkResponseWithData()
            if (response.success == true) {
                val currencyRates = CurrencyRates.values().toList()
                currencyRates.forEach { it.rate = it.toRateAmount(response.rates!!) }
                postState(CurrencyRatesViewState.OnCurrencyReceived(currencyRates))
            }
        }
    }

    private fun filterList(filteredText: String?) {
        if (filteredText == null) {
            return
        }
        val filteredList = viewStateData.currency.filter {
            it.name.uppercase(Locale.getDefault())
                .contains(filteredText.uppercase(Locale.getDefault()))
        }
        postState(CurrencyRatesViewState.OnListFiltered(filteredList))
    }
}