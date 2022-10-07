package com.androida.currencyexchanger.domain.di

import android.content.Context
import androidx.room.Room
import com.androida.currencyexchanger.core.custom.consts.CURRENCY_RATE
import com.androida.currencyexchanger.data.database.CurrencyRatesDbApi
import com.androida.currencyexchanger.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext context: Context
    ): Database = Room
        .databaseBuilder(context, Database::class.java, CURRENCY_RATE)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCurrencyRatesDao(database: Database): CurrencyRatesDbApi =
        database.getCurrencyRatesDao()

}