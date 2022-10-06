package com.androida.currencyexchanger.core.fragment.utils.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.androida.currencyexchanger.R

sealed class DialogOptions(
    val title: String?,
    @StringRes open val titleRes: Int?,
    val message: String?,
    @StringRes val messageRes: Int?,
    @DrawableRes val icon: Int
) {
    class GeneralError(error: String) : DialogOptions(
        title = null,
        titleRes = R.string.warning_general_title,
        messageRes = R.string.warning_general_message,
        message = error,
        icon = com.google.android.material.R.drawable.mtrl_ic_error
    )

}
