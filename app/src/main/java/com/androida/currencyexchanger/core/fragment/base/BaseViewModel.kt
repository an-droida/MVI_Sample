package com.androida.currencyexchanger.core.fragment.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.handlers.CommandHandler
import com.androida.currencyexchanger.core.custom.extensions.log
import com.androida.currencyexchanger.core.custom.extensions.logAction
import com.androida.currencyexchanger.core.custom.extensions.logMessage
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.state.BaseViewState
import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.core.fragment.utils.exceptions.ApiException
import com.androida.currencyexchanger.core.fragment.utils.getErrorMessage
import dagger.Lazy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
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
            execute {
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


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.logMessage.log("view model exception")
        executeWarningCommand(exception)
        executeLoaderCommand(false)
    }

    private fun executeWarningCommand(exception: Throwable) {
        val defaultMessage = "Something went wrong"
        val message = if (exception is ApiException)
            exception.message ?: defaultMessage
        else
            defaultMessage
        val command = object : Command.Warning {
            override val message: DialogOptions = DialogOptions.GeneralError(error = message)
        }
        commandHandler.get().handleCommand(command)
    }

    private fun executeLoaderCommand(show: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            val command = object : Command.Loader {
                override val show: Boolean = show
            }
            commandHandler.get().handleCommand(command)
        }
    }


    protected fun execute(
        withLoader: Boolean = false,
        callback: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            if (withLoader) {
                executeLoaderCommand(true)
                delay(400)
            }
            this.callback()
            executeLoaderCommand(false)
        }
    }

    protected fun <T> Response<T>.checkResponseWithData(): T {
        val body = body()
        return when {
            isSuccessful -> {
                body!!
            }
            else -> {
                // TODO can handle error codes here
                throw ApiException(getErrorMessage())
            }
        }
    }
}