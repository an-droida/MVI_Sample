package com.androida.currencyexchanger.domain.di

import com.androida.currencyexchanger.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    abstract fun bindCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    abstract fun bindCurrencyRatesRepository(repository: MyBalanceRepositoryImpl): MyBalanceRepository

    @Binds
    abstract fun bindCurrencyPreferenceRepository(repository: CurrencyPreferenceRepositoryImpl): CurrencyPreferenceRepository

}
