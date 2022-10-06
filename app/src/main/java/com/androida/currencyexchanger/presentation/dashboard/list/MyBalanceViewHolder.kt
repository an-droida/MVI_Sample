package com.androida.currencyexchanger.presentation.dashboard.list

import androidx.recyclerview.widget.RecyclerView
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import com.androida.currencyexchanger.databinding.ItemMyBalanceBinding

class MyBalanceViewHolder(private val viewBinding: ItemMyBalanceBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun onBind(data: MyBalanceModel) {
        viewBinding.tvBalance.text = data.balance
        viewBinding.tvCurrency.text = data.currency

    }
}