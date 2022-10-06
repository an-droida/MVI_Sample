package com.androida.currencyexchanger.core.custom.extensions

import android.util.Log
import androidx.lifecycle.ViewModel

fun Any?.log(key: String) {
    Log.e(key, toString())
}

val Throwable.logMessage: String get() = (message ?: "") + "\n" + stackTraceToString()

fun ViewModel.logAction(action: Any) =
    "${this::class.java.simpleName} - Action - ${action.javaClass.simpleName}"