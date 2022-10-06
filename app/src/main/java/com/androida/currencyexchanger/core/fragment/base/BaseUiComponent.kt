package com.androida.currencyexchanger.core.fragment.base

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.androida.currencyexchanger.core.custom.extensions.log
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.state.BaseViewState
import com.androida.currencyexchanger.core.fragment.utils.ViewBindingFactory
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.reflect.KClass

interface BaseUiComponent<
        ViewBindingType : ViewBinding,
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> {

    val viewModelClass: KClass<ViewModel>
    val viewBindingFactory: ViewBindingFactory<ViewBindingType>

    fun postAction(action: Action)

    fun reflectState(state: ViewState, viewBinding: ViewBindingType) {}

    fun onDraw(view: View, lastState: ViewStateData?, viewBinding: ViewBindingType) {}

    fun BaseUiComponent<ViewBindingType, Action, ViewState, ViewModel, ViewStateData>.collectStates(
        fragment: Fragment,
        viewBinding: () -> ViewBindingType?,
        viewModel: ViewModel
    ) {
        viewModel.restoreStateSubject
            .filter {
                fragment.view != null
            }.onEach {
                onDraw(fragment.requireView(), it, viewBinding()!!)
            }.launchIn(fragment.lifecycleScope)

        viewModel.stateSubject
            .filter {
                fragment.view != null
            }.onEach {
                "${this::class.java.simpleName} - State - ${it.javaClass.simpleName}".log("MVI")
                reflectState(it, viewBinding()!!)
            }.launchIn(fragment.lifecycleScope)
    }
}