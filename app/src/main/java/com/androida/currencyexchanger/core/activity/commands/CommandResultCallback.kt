package com.androida.currencyexchanger.core.activity.commands

class CommandResultCallback(
    val callback: (CommandResult) -> Unit,
    val viewModelTag: String
)