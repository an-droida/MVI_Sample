package com.androida.currencyexchanger.presentation.dashboard.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import com.androida.currencyexchanger.databinding.ItemMyBalanceBinding
import com.androida.currencyexchanger.presentation.currency.list.CurrencyRatesViewHolder

class MyBalanceAdapter : RecyclerView.Adapter<MyBalanceViewHolder>() {

    private var pages = emptyList<MyBalanceModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<MyBalanceModel>) {
        pages = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBalanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyBalanceBinding.inflate(inflater, parent, false)
        return MyBalanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyBalanceViewHolder, position: Int) {
        holder.onBind(pages[position])
    }

    override fun getItemCount() = pages.size

}
