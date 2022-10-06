package com.androida.currencyexchanger.core.custom.managers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.androida.currencyexchanger.R
import com.androida.currencyexchanger.core.fragment.base.fragment.BaseFragment
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomFragmentManager @Inject constructor(@ActivityContext private val context: Context) {

    private val supportFragmentManager: FragmentManager =
        (context as AppCompatActivity).supportFragmentManager

    fun queryAllBaseFragments(callback: BaseFragment<*, *, *, *, *>.() -> Unit) {
        supportFragmentManager.fragments.filterIsInstance<BaseFragment<*, *, *, *, *>>()
            .forEach {
                it.callback()
            }
    }

    fun getLastBaseFragment(): BaseFragment<*, *, *, *, *>? {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentHolder)
        return if (fragment is BaseFragment<*, *, *, *, *>)
            fragment
        else
            null
    }

    fun getLastDialogFragment(): DialogFragment? {
        val lastFragment = supportFragmentManager.fragments.lastOrNull()
        return if (lastFragment is DialogFragment)
            lastFragment
        else
            null
    }

    fun addBackStackListener(callback: () -> Unit) {
        supportFragmentManager.addOnBackStackChangedListener(callback)
    }

    fun getFragmentByTag(fragmentTag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }

    fun executeNavigation(item: NavigationOptions, context: Context) {
        when {
            item.fragmentCreator != null ->
                FragmentTransactionManager.drawFragment(item, supportFragmentManager)
            item.bottomSheetCreator != null ->
                FragmentTransactionManager.drawBottomSheetFragment(item, supportFragmentManager)
        }
    }

    fun popBackStack(upTo: NavigationOptions?, context: Context) {
        when {
            upTo == null -> supportFragmentManager.popBackStack()
            supportFragmentManager.findFragmentByTag(upTo.fragmentTag) == null ->
                executeNavigation(upTo, context)
            else -> supportFragmentManager.popBackStack(upTo.fragmentTag, 0)
        }
    }

    fun isBackStackEmpty(): Boolean {
        return supportFragmentManager.backStackEntryCount == 1
    }

}
