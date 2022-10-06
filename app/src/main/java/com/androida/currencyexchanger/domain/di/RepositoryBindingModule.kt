package com.androida.currencyexchanger.domain.di

import com.androida.currencyexchanger.domain.repositories.MyBalanceRepository
import com.androida.currencyexchanger.domain.repositories.MyBalanceRepositoryImpl
import com.androida.currencyexchanger.domain.repositories.CurrencyRepository
import com.androida.currencyexchanger.domain.repositories.CurrencyRepositoryImpl
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

}
