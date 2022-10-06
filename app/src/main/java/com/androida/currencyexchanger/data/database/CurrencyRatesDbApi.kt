package com.androida.currencyexchanger.data.database

import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import kotlinx.coroutines.flow.Flow

interface CurrencyRatesDbApi {

    suspend fun saveOrUpdateData(myBalance: MyBalanceModel)

    suspend fun clearData()

    fun getAllData(): Flow<List<MyBalanceModel>>

    fun findMyBalanceCurrency(currency:String): MyBalanceModel?

    suspend fun exchangeBalance(
        amount: String,
        currency: String,
    )

}