package com.androida.currencyexchanger.domain.repositories

import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import kotlinx.coroutines.flow.Flow

interface MyBalanceRepository {

    suspend fun saveOrUpdateData(currencyRatesModel: MyBalanceModel)

    suspend fun clearData()

    fun getAllData(): Flow<List<MyBalanceModel>>

    suspend fun findMyBalanceCurrency(currency: String): MyBalanceModel?

    suspend fun exchangeBalance(
        amount: String,
        currency: String,
    )

}