package com.androida.currencyexchanger.core.custom.managers

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions

object FragmentTransactionManager {

    fun drawBottomSheetFragment(item: NavigationOptions, fragmentManager: FragmentManager) {
        item.bottomSheetCreator?.invoke().apply {
            if (item.argument != null) {
                (this as Fragment).arguments = bundleOf(item.fragmentTag to item.argument)
            }
        }?.show(fragmentManager, item.fragmentTag)

    }

    fun drawFragment(
        navOptions: NavigationOptions,
        fragmentManager: FragmentManager
    ) {
        fragmentManager.commit(allowStateLoss = true) {

            if (navOptions.popBackStack != null)
                when (navOptions.popBackStack) {
                    is PopBackStack.Last -> {
                        fragmentManager.popBackStack()
                    }
                    is PopBackStack.Clear -> {
                        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    }
                    is PopBackStack.From -> {
                        fragmentManager.popBackStack(
                            (navOptions.popBackStack as PopBackStack.From).name,
                            0
                        )
                    }
                    else -> {}
                }

            var fragment = navOptions.fragmentCreator!!.invoke()

            if (navOptions.argument != null) {
                fragment = fragment.apply {
                    (this as Fragment).arguments =
                        bundleOf(navOptions.fragmentTag to navOptions.argument)
                }
            }

            replace(R.id.fragmentHolder, fragment, navOptions.fragmentTag)

            if (navOptions.addToBackStack) {
                addToBackStack(navOptions.fragmentTag)
            }
        }
    }

    sealed class PopBackStack {
        object Last : PopBackStack()
        object Clear : PopBackStack()
        class From(val name: String) : PopBackStack()
    }

}