package com.androida.currencyexchanger.core.custom.extensions

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager


@Suppress("DEPRECATION")
fun WindowManager.getWindowWidth() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        currentWindowMetrics.bounds.right
    } else {
        val displayMetrics = DisplayMetrics()
        defaultDisplay?.getRealMetrics(displayMetrics)
        displayMetrics.widthPixels
    }

@Suppress("DEPRECATION")
fun WindowManager.getWindowHeight() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        currentWindowMetrics.bounds.bottom
    } else {
        val displayMetrics = DisplayMetrics()
        defaultDisplay?.getRealMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
