package com.androida.currencyexchanger.core.fragment.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.handlers.CommandHandler
import com.androida.currencyexchanger.core.custom.extensions.log
import com.androida.currencyexchanger.core.custom.extensions.logAction
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.state.BaseViewState
import dagger.Lazy
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<
        ViewAction : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewStateData> : ViewModel() {

    abstract var viewStateData: ViewStateData

    private val _stateSubject: MutableSharedFlow<ViewState> = MutableSharedFlow()
    val stateSubject = _stateSubject.asSharedFlow()

    private val _restoreStateSubject: MutableSharedFlow<ViewStateData> = MutableSharedFlow()
    val restoreStateSubject = _restoreStateSubject.asSharedFlow()

    private val actionSubject: MutableSharedFlow<ViewAction> = MutableSharedFlow()

    private var isViewInitialized = false

    init {
        actionSubject
            .onEach(::processAction)
            .launchIn(viewModelScope)
    }

    @Inject
    lateinit var commandHandler: Lazy<CommandHandler>

    @Suppress("UNCHECKED_CAST")
    private fun processAction(viewAction: ViewAction) {
        logAction(viewAction).log("MVI")

        if (viewAction is Command)
            commandHandler.get().handleCommand(viewAction)
        else
            onActionReceived(viewAction)
    }

    protected open fun onActionReceived(action: ViewAction) {}

    override fun onCleared() {
        this.commandHandler.get().removeCommandResultCallback(this::class.java.simpleName)
        super.onCleared()
    }


    @Suppress("UNCHECKED_CAST")
    fun initializeViewState(initialize: () -> Unit) {
        this.commandHandler.get().addCommandResultCallback(this::class.java.simpleName) {
            postAction(it as ViewAction)
        }
        if (isViewInitialized) {
            viewModelScope.launch {
                _restoreStateSubject.emit(viewStateData)
            }
        } else {
            initialize.invoke()
            onInitialBind()
        }
        isViewInitialized = true
    }

    open fun onInitialBind() {}

    fun postAction(action: ViewAction) {
        viewModelScope.launch {
            viewStateData = action.stateReducer(viewStateData)
            actionSubject.emit(action)
        }
    }

    fun postState(state: ViewState) {
        viewModelScope.launch {
            viewStateData = state.stateReducer(viewStateData)
            _stateSubject.emit(state)
        }
    }

}