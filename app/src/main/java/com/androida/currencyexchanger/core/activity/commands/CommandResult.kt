package com.androida.currencyexchanger.core.activity.commands

sealed interface CommandResult {
    val viewModelTag: String


    interface ObservableFragmentAction<T> : CommandResult {
        var data: T?
        val fragmentTag: String?
        val type: ObservableActionTypes
    }
}