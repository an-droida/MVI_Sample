package com.androida.currencyexchanger.core.custom.extensions

import android.view.ViewGroup
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Fragment.getWidthPercent(percent: Float): Int {
    val widthPixels = requireActivity().windowManager.getWindowWidth()
    return (widthPixels * percent).toInt()
}

fun Fragment.getHeightPercent(percent: Float, withSystemUI: Boolean = false): Int {
    val decorView = requireActivity().window.decorView
    val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
    val mainViewHolder = rootView.getChildAt(0) as ViewGroup
    val mainView = mainViewHolder.getChildAt(0)
    val height = if (withSystemUI)
        requireActivity().windowManager.getWindowHeight()
    else
        mainView.height
    return (height * percent).toInt()
}

fun Fragment.delayUI(@IntegerRes durationRes: Int, callback: () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        delay(resources.getInteger(durationRes).toLong())
        callback()
    }
}
