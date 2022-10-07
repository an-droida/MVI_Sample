package com.androida.currencyexchanger.presentation.currency.list

import androidx.recyclerview.widget.RecyclerView
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.core.custom.extensions.onClick
import com.androida.currencyexchanger.databinding.ItemCurrencyBinding

class CurrencyRatesViewHolder(private val viewBinding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun onBind(data: CurrencyRates, onItemSelected: (CurrencyRates) -> Unit) {
        viewBinding.tvCurrency.text = data.currency
        viewBinding.tvRate.text = "${data.rate.toString()} ${data.baseCurrency}"
        viewBinding.root.onClick {
            onItemSelected.invoke(data)
        }
    }
}