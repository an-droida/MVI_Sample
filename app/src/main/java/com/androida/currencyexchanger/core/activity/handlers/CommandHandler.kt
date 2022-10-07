package com.androida.currencyexchanger.core.activity.handlers

import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.CommandResult


interface CommandHandler {

    fun removeCommandResultCallback(viewModelTag: String)

    fun addCommandResultCallback(
        viewModelTag: String,
        callback: (CommandResult) -> Unit
    )

    fun handleCommand(command: Command)

}
