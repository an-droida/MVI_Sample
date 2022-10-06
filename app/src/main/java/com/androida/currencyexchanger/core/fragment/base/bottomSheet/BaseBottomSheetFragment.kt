package com.androida.currencyexchanger.core.fragment.base.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.custom.extensions.getHeightPercent
import com.androida.currencyexchanger.core.fragment.action.BaseAction
import com.androida.currencyexchanger.core.fragment.base.BaseUiComponent
import com.androida.currencyexchanger.core.fragment.base.BaseViewModel
import com.androida.currencyexchanger.core.fragment.state.BaseViewState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<
        ViewBindingType : ViewBinding,
        Action : BaseAction<ViewStateData>,
        ViewState : BaseViewState<ViewStateData>,
        ViewModel : BaseViewModel<Action, ViewState, ViewStateData>,
        ViewStateData> :
    BaseUiComponent<ViewBindingType, Action, ViewState, ViewModel, ViewStateData>,
    BottomSheetDialogFragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    protected open val peekHeightPercent: Float = 1f
    protected open val heightPercent: Float = 1f
    protected open val isDismissible: Boolean = true


    private val viewModel: ViewModel by lazy {
        createViewModelLazy(viewModelClass, { viewModelStore }).value
    }
    private var viewBinding: ViewBindingType? = null
    override fun postAction(action: Action) {
        viewModel.postAction(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BottomSheetFragment)
        collectStates(this, { viewBinding }, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isDismissible
        viewBinding = viewBindingFactory.invoke(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeDialog()
        viewModel.initializeViewState {
            onDraw(view, null, viewBinding!!)
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

    open fun onDialogDismissed() {}

    protected fun dismissBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun customizeDialog() {
        dialog?.setOnShowListener {
            val dialog = dialog as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val coordinatorLayout = bottomSheet.parent as? CoordinatorLayout
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

                bottomSheetBehavior.peekHeight = getHeightPercent(peekHeightPercent)
                bottomSheet.updateLayoutParams {
                    height = getHeightPercent(heightPercent)
                }
                coordinatorLayout?.parent?.requestLayout()
            }
        }
        dialog?.setOnDismissListener {
            onDialogDismissed()
            dismissAllowingStateLoss()
        }
    }

}