package com.androida.currencyexchanger.core.activity.handlers

import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.CommandResult
import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions

interface MainHandler {

    fun executeNavigation(item: NavigationOptions)
    fun showWarning(message: DialogOptions)
    fun popBackStack(upTo: NavigationOptions?)
    fun onHomeActionReceived(item: Command.ObservableFragmentAction<Any>)
    fun registerHomeAction(
        pendingAction: CommandResult.ObservableFragmentAction<Any>,
        callback: (CommandResult.ObservableFragmentAction<Any>) -> Unit
    )
    fun toggleLoader(show: Boolean)
}
