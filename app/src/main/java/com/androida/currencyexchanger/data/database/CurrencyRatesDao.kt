package com.androida.currencyexchanger.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao : CurrencyRatesDbApi {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun saveOrUpdateData(myBalance: MyBalanceModel)

    @Query("DELETE from my_balance")
    override suspend fun clearData()

    @Query("SELECT * from my_balance")
    override fun getAllData(): Flow<List<MyBalanceModel>>

    @Query("UPDATE my_balance SET balance=:amount WHERE currency == :currency")
    override suspend fun exchangeBalance(
        amount: String,
        currency: String,
    )

    @Query("SELECT * from my_balance WHERE currency=:currency")
    override suspend fun findMyBalanceCurrency(currency: String): MyBalanceModel?
}