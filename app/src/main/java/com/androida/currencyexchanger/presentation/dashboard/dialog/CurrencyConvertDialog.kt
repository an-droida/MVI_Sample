package com.androida.currencyexchanger.presentation.dashboard.dialog

import android.app.Dialog
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.custom.extensions.calculateCommissionFee
import com.androida.currencyexchanger.core.custom.extensions.onClick
import com.androida.currencyexchanger.databinding.DialogCurrencyConvertBinding

fun Fragment.openCurrencyConvertDialog(
    currencyFrom: String,
    currencyTo: String,
    sell: Double,
    receive: Double,
    convertCount: Int,
    onConfirmClicked: (Double, Double) -> Unit
) {
    Dialog(requireContext()).apply {

        val binding = DialogCurrencyConvertBinding.inflate(
            layoutInflater, requireView() as ViewGroup, false
        )

        with(binding) {

            val commissionFee = sell.calculateCommissionFee(convertCount)
            val totalSell = sell + commissionFee

            tvSubtitle.text = getString(
                R.string.converted_currency_subtitle,
                currencyFrom,
                sell,
                currencyTo,
                receive,
                commissionFee,
                currencyFrom
            )
            tvDone.onClick {
                onConfirmClicked.invoke(totalSell, receive)
                dismiss()
            }
            setContentView(root)
        }

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        show()
    }
}