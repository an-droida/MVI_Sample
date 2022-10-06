package com.androida.currencyexchanger.domain.di

import com.androida.currencyexchanger.data.preferences.CurrencyPreferences
import com.androida.currencyexchanger.data.preferences.CurrencyPreferencesApi
import com.androida.currencyexchanger.data.preferences.manager.PreferencesManager
import com.androida.currencyexchanger.data.preferences.manager.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindPreferencesManager(managerImpl: PreferencesManagerImpl): PreferencesManager

    @Binds
    abstract fun bindPreferencesProvider(preferences: CurrencyPreferences): CurrencyPreferencesApi

}