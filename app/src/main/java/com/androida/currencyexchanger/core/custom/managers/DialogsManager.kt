package com.androida.currencyexchanger.core.custom.managers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.androida.currencyexchanger.core.custom.extensions.onClick
import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.databinding.LayoutSnackbarBinding
import com.google.android.material.snackbar.Snackbar

object DialogsManager {

    fun showToast(context: Context, @StringRes messageRes: Int) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("InflateParams")
    fun showSnackBar(
        activity: Activity,
        bottomSheet: DialogFragment?,
        option: DialogOptions
    ) {
        val contentView: View = bottomSheet?.dialog?.window?.decorView ?: activity.window.decorView
        val snackBar = Snackbar.make(contentView, "", Snackbar.LENGTH_LONG)
        val layout = snackBar.view as Snackbar.SnackbarLayout
        val customLayout = LayoutSnackbarBinding.inflate(activity.layoutInflater)

        with(layout) {
            getChildAt(0).visibility = View.INVISIBLE
            setBackgroundResource(android.R.color.transparent)
            setPadding(0, 0, 0, 0)
            addView(customLayout.root, 0)
        }

        with(customLayout) {
            tvTitle.text = option.title ?: activity.getString(option.titleRes ?: 0)
            tvMessage.text = option.message ?: activity.getString(option.messageRes ?: 0)
             ivClose.onClick {
                snackBar.dismiss()
            }
        }

        snackBar.show()
    }
}

