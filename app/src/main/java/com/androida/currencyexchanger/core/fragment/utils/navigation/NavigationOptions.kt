package com.androida.currencyexchanger.core.fragment.utils.navigation

import android.os.Parcelable
import com.androida.currencyexchanger.core.custom.managers.FragmentTransactionManager
import com.androida.currencyexchanger.core.fragment.base.bottomSheet.BaseBottomSheetFragment
import com.androida.currencyexchanger.core.fragment.base.fragment.BaseFragment
import com.androida.currencyexchanger.presentation.currency.CurrencyRatesFragment
import com.androida.currencyexchanger.presentation.dashboard.DashboardFragment
import java.io.Serializable


sealed class NavigationOptions constructor(
    open val fragmentTag: String,
    open val fragmentCreator: (() -> BaseFragment<*, *, *, *, *>)? = null,
    open val addToBackStack: Boolean,
    open var argument: Serializable? = null,
    open val popBackStack: FragmentTransactionManager.PopBackStack? = null,
    open val bottomSheetCreator: (() -> BaseBottomSheetFragment<*, *, *, *, *>)? = null
) {

    class DashboardFragmentOption :
        NavigationOptions(
            fragmentTag = "DashboardFragment",
            fragmentCreator = { DashboardFragment() },
            addToBackStack = true
        )

    class CurrencyRatesFragmentOption(override var argument: Serializable?) :
        NavigationOptions(
            fragmentTag = "CurrencyRatesFragment",
            bottomSheetCreator = { CurrencyRatesFragment() },
            addToBackStack = false
        )


}