package com.androida.currencyexchanger.presentation.currency.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.databinding.ItemCurrencyBinding

class CurrencyRatesAdapter(private val onItemSelected: (CurrencyRates) -> Unit) :
    RecyclerView.Adapter<CurrencyRatesViewHolder>() {

    private var currencies = emptyList<CurrencyRates>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<CurrencyRates>) {
        currencies = data
        notifyDataSetChanged()
    }

    fun getAllData(): List<CurrencyRates> {
        return currencies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return CurrencyRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyRatesViewHolder, position: Int) {
        holder.onBind(currencies[position], onItemSelected)
    }

    override fun getItemCount() = currencies.size

}
