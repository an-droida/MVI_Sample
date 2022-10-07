package com.androida.currencyexchanger.domain.di

import com.androida.currencyexchanger.core.activity.handlers.CommandHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    lateinit var commandHandler: CommandHandler

    @Provides
    @ViewModelScoped
    fun provideCommandHandler(): CommandHandler {
        return commandHandler
    }

}