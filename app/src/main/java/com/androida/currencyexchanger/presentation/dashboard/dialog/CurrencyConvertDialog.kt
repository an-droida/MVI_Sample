package com.androida.currencyexchanger.presentation.dashboard.dialog

import android.app.Dialog
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.custom.consts.COMMISSION_FEE
import com.androida.currencyexchanger.core.custom.extensions.onClick
import com.androida.currencyexchanger.databinding.DialogCurrencyConvertBinding

fun Fragment.openCurrencyConvertDialog(
    currencyFrom: String,
    currencyTo: String,
    sell: String,
    receive: String,
    convertCount: Int,
    onConfirmClicked: (String, String) -> Unit
) {
    Dialog(requireContext()).apply {
        val binding = DialogCurrencyConvertBinding.inflate(
            layoutInflater,
            requireView() as ViewGroup,
            false
        )

        with(binding) {

            val commissionFee: Double =
                if (convertCount > 5) {
                    val fee = (sell.toDouble() * COMMISSION_FEE)
                    String.format("%.3f", fee).toDouble()
                } else {
                    0.0
                }

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
                onConfirmClicked.invoke(
                    (sell.toDouble() + commissionFee).toString(), receive
                )
                dismiss()
            }
            setContentView(root)
        }

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        show()
    }
}