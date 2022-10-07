package com.androida.currencyexchanger.core.activity.handlers

import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.custom.extensions.log
import com.androida.currencyexchanger.core.custom.extensions.logMessage
import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.core.fragment.utils.exceptions.ApiException
import dagger.Lazy
import kotlinx.coroutines.*
import javax.inject.Inject

class SideEffectHandler @Inject constructor() {

    @Inject
    lateinit var commandHandler: Lazy<CommandHandler>

    fun execute(
        scope: CoroutineScope,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        withLoader: Boolean = false,
        backgroundTask: suspend SideEffectHandler.() -> Unit,
    ) {
        scope.launch(exceptionHandler + coroutineDispatcher) {
            if (withLoader) {
                executeLoaderCommand(true)
                delay(400)
            }
            backgroundTask()
            executeLoaderCommand(false)
        }
    }

    private fun executeOnErrorUiCommands(errorMsg: String) {
        executeWarningCommand(errorMsg)
        executeLoaderCommand(show = false)
    }

    private fun executeWarningCommand(errorMsg: String) {
        val command = object : Command.Warning {
            override val message: DialogOptions = DialogOptions.GeneralError(error = errorMsg)
        }
        commandHandler.get().handleCommand(command)
    }

    private fun executeLoaderCommand(show: Boolean) = CoroutineScope(Dispatchers.Main).launch {
        val command = object : Command.Loader {
            override val show: Boolean = show
        }
        commandHandler.get().handleCommand(command)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.logMessage.log("view model exception")
        executeOnErrorUiCommands(errorMsg = exception.toErrorMsg())
    }

    private fun Throwable.toErrorMsg(): String {
        val defaultMessage = "Something went wrong"
        return if (this is ApiException) message ?: defaultMessage
        else defaultMessage
    }
}
