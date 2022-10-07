package com.androida.currencyexchanger.core.activity

import androidx.lifecycle.ViewModel
import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.CommandResult
import com.androida.currencyexchanger.core.activity.commands.CommandResultCallback
import com.androida.currencyexchanger.core.activity.handlers.CommandHandler
import com.androida.currencyexchanger.core.activity.handlers.MainHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel(), CommandHandler {
    private var commandResultCallback = mutableListOf<CommandResultCallback>()

    private var mainHandler: WeakReference<MainHandler>? = null

    fun bindView(mainHandler: MainHandler) {
        this.mainHandler = WeakReference(mainHandler)
        onBind()
    }

    fun unbindView() {
        mainHandler = null
    }

    private fun requireHandler(callback: (mainHandler: MainHandler) -> Unit) {
        mainHandler?.get()?.run {
            callback.invoke(this)
        }
    }

    private fun onBind() {}

    override fun removeCommandResultCallback(viewModelTag: String) {
        commandResultCallback.removeAll { it.viewModelTag == viewModelTag }
    }

    override fun addCommandResultCallback(
        viewModelTag: String,
        callback: (CommandResult) -> Unit
    ) {
        commandResultCallback.add(CommandResultCallback(callback, viewModelTag))
    }

    private fun sendCommandResult(result: CommandResult) {
        commandResultCallback.find {
            it.viewModelTag == result.viewModelTag
        }?.callback?.invoke(result)
    }

    @Suppress("UNCHECKED_CAST")
    override fun handleCommand(command: Command) {
        requireHandler { mainHandler ->
            when (command) {
                is Command.Navigation -> {
                    mainHandler.toggleLoader(false)
                    mainHandler.executeNavigation(command.option)
                }
                is Command.Warning -> {
                    mainHandler.showWarning(command.message)
                }
                is Command.PopBackStack -> {
                    mainHandler.popBackStack(command.upTo)
                }
                is Command.RegisterFragmentAction<*> -> {
                    mainHandler.registerHomeAction(command.getCommandResult() as CommandResult.ObservableFragmentAction<Any>) {
                        sendCommandResult(it)
                    }
                }
                is Command.ObservableFragmentAction<*> -> {
                    mainHandler.onHomeActionReceived(command as Command.ObservableFragmentAction<Any>)
                }
                is Command.Loader -> {
                    mainHandler.toggleLoader(command.show)
                }
            }
        }
    }

}
