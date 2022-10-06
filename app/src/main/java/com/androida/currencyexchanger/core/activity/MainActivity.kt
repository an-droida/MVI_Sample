package com.androida.currencyexchanger.core.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.androida.currencyexchanger.core.activity.commands.Command
import com.androida.currencyexchanger.core.activity.commands.CommandResult
import com.androida.currencyexchanger.core.activity.handlers.MainHandler
import com.androida.currencyexchanger.core.custom.managers.CommandResultManager
import com.androida.currencyexchanger.core.custom.managers.CustomFragmentManager
import com.androida.currencyexchanger.core.custom.managers.DialogsManager
import com.androida.currencyexchanger.core.fragment.utils.dialog.DialogOptions
import com.androida.currencyexchanger.core.fragment.utils.dialog.LoaderDialog
import com.androida.currencyexchanger.core.fragment.utils.navigation.NavigationOptions
import com.androida.currencyexchanger.databinding.ActivityMainBinding
import com.androida.currencyexchanger.domain.di.ViewModelModule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainHandler {

    private lateinit var viewBinding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private var loaderDialog: LoaderDialog? = null

    @Inject
    lateinit var commandResultManager: CommandResultManager

    @Inject
    lateinit var customFragmentManager: CustomFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelModule.commandHandler = viewModel
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel.bindView(this)
        handleOnBackPressed()
        registerBackStackListener()
        executeNavigation(NavigationOptions.DashboardFragmentOption())
    }

    override fun executeNavigation(item: NavigationOptions) {
        customFragmentManager.executeNavigation(item, this)
    }

    override fun showWarning(message: DialogOptions) {
        val currentDialogFragment = customFragmentManager.getLastDialogFragment()
        DialogsManager.showSnackBar(this, currentDialogFragment, message)
    }

    override fun popBackStack(upTo: NavigationOptions?) {
        when {
            upTo == null -> supportFragmentManager.popBackStack()
            supportFragmentManager.findFragmentByTag(upTo.fragmentTag) == null ->
                executeNavigation(upTo)
            else -> supportFragmentManager.popBackStack(upTo.fragmentTag, 0)
        }
    }


    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val lastFragment = customFragmentManager.getLastBaseFragment()
                val isActionConsumed = lastFragment?.backButtonPressed() ?: false
                if (!isActionConsumed)
                    when {
                        customFragmentManager.isBackStackEmpty() -> moveTaskToBack(true)
                        else -> onBackPressed()
                    }

            }
        })
    }

    private fun registerBackStackListener() {
        customFragmentManager.addBackStackListener(this::syncHomeActionObservers)
    }

    private fun syncHomeActionObservers() {
        commandResultManager.syncCommandResultObservers(customFragmentManager::getFragmentByTag)
    }

    override fun onHomeActionReceived(item: Command.ObservableFragmentAction<Any>) {
        commandResultManager.notify(item.type, item.data)
    }

    override fun registerHomeAction(
        pendingAction: CommandResult.ObservableFragmentAction<Any>,
        callback: (CommandResult.ObservableFragmentAction<Any>) -> Unit
    ) {
        commandResultManager.register(pendingAction, callback)
    }

    override fun toggleLoader(show: Boolean) {
        loaderDialog?.dismiss()
        loaderDialog = null
        if (show) {
            loaderDialog = LoaderDialog(this).showDialog()
        }
    }

    override fun onDestroy() {
        viewModel.unbindView()
        super.onDestroy()

    }
}