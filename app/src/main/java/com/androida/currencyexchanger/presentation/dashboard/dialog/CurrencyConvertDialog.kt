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
    onConfirmClicked: (String, String) -> Unit
) {
    Dialog(requireContext()).apply {
        val binding = DialogCurrencyConvertBinding.inflate(
            layoutInflater,
            requireView() as ViewGroup,
            false
        )

        with(binding) {

            val commissionFee = (sell.toDouble() * COMMISSION_FEE)
            val commissionFeeRounded = String.format("%.3f", commissionFee)

            tvSubtitle.text = getString(
                R.string.converted_currency_subtitle,
                currencyFrom,
                sell,
                currencyTo,
                receive,
                commissionFeeRounded,
                currencyFrom
            )
            tvDone.onClick {
                onConfirmClicked.invoke(
                    (sell.toDouble() + commissionFeeRounded.toDouble()).toString(),
                    receive
                )
                dismiss()
            }
            setContentView(root)
        }

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        show()
    }
}