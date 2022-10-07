package com.androida.currencyexchanger.core.activity.commands

import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions

sealed interface Command {

    fun getCommandResult(): CommandResult?

    interface Navigation : Command {
        val option: NavigationOptions
        override fun getCommandResult(): CommandResult? = null
    }

    interface Loader : Command {
        val show: Boolean
        override fun getCommandResult(): CommandResult? = null
    }

    interface Warning : Command {
        val message: DialogOptions
        override fun getCommandResult(): CommandResult? = null
    }

    interface PopBackStack : Command {
        val upTo: NavigationOptions?
        override fun getCommandResult(): CommandResult? = null
    }

    interface RegisterFragmentAction<T> : Command {
        override fun getCommandResult(): CommandResult.ObservableFragmentAction<T>
    }

    interface ObservableFragmentAction<T> : Command {
        var data: T?
        val type: ObservableActionTypes
        override fun getCommandResult(): CommandResult? = null
    }
}