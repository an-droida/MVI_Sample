package com.androida.currencyexchanger.core.custom.managers

import androidx.fragment.app.Fragment
import com.androida.currencyexchanger.core.activity.commands.CommandResult
import com.androida.currencyexchanger.core.activity.commands.ObservableActionTypes
import javax.inject.Inject

class CommandResultManager @Inject constructor() {

    private val commandResultObservers: MutableList<Pair<CommandResult.ObservableFragmentAction<Any>, (CommandResult.ObservableFragmentAction<Any>) -> Unit>> =
        mutableListOf()

    fun notify(type: ObservableActionTypes, data: Any? = Unit) {
        commandResultObservers.filter {
            it.first.type == type
        }.forEach {
            if (data != null) {
                it.second.invoke(it.first.apply {
                    this.data = data
                })
            }
        }
    }

    fun register(
        pendingAction: CommandResult.ObservableFragmentAction<Any>,
        callback: (CommandResult.ObservableFragmentAction<Any>) -> Unit
    ) {
        val previousCallback = commandResultObservers.find {
            it.first.type == pendingAction.type && it.first.fragmentTag == pendingAction.fragmentTag
        }
        if (previousCallback != null)
            commandResultObservers.remove(previousCallback)
        commandResultObservers.add(pendingAction to callback)
    }

    fun syncCommandResultObservers(callback: (String) -> Fragment?) {
        commandResultObservers.removeAll { observer ->
            callback.invoke(observer.first.fragmentTag.orEmpty()) == null
        }
    }

}
