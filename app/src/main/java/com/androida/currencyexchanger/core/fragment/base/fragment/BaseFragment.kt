package com.androida.currencyexchanger.core.fragment.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.viewbinding.ViewBinding
import com.androida.currencyexchanger.core.activity.MainActivity
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.base.BaseUiComponent
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.core.fragment.state.BaseViewState

abstract class BaseFragment<ViewBindingType : ViewBinding,
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> :
    BaseUiComponent<ViewBindingType, Action, ViewState, ViewModel, ViewStateData>, Fragment() {

    private var viewBinding: ViewBindingType? = null

    private val viewModel: ViewModel by lazy {
        createViewModelLazy(viewModelClass, { viewModelStore }).value
    }

    override fun postAction(action: Action) {
        viewModel.postAction(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectStates(this, { viewBinding }, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = viewBindingFactory.invoke(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initializeViewState {
            onDraw(view, null, viewBinding!!)
        }
    }

    override fun onDraw(view: View, lastState: ViewStateData?, viewBinding: ViewBindingType) {}

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    open fun onKeyboardOpened(isOpen: Boolean) = Unit

    open fun backButtonPressed(): Boolean = false

    protected fun requireBinding(callback: (viewBinding: ViewBindingType) -> Any) {
        viewBinding?.let(callback)
    }

//    fun View.bindActionOnClick(action: () -> Action) {
//        onClick {
//            SystemServices.getKeyboardService(requireActivity()).hideKeyboard()
//            postAction(action.invoke())
//        }
//    }

}