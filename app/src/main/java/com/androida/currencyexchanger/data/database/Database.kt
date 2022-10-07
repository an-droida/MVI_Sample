package com.androida.currencyexchanger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androida.currencyexchanger.data.models.local.MyBalanceModel

@Database(
    entities = [MyBalanceModel::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun getCurrencyRatesDao(): CurrencyRatesDao
}