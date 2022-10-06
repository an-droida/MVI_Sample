package com.androida.currencyexchanger.domain.repositories

import com.androida.currencyexchanger.data.database.CurrencyRatesDbApi
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MyBalanceRepositoryImpl @Inject constructor(
    private val currencyRatesDbApi: CurrencyRatesDbApi
) : MyBalanceRepository {

    override suspend fun saveOrUpdateData(currencyRatesModel: MyBalanceModel) {
        return currencyRatesDbApi.saveOrUpdateData(currencyRatesModel)
    }

    override suspend fun clearData() {
        return currencyRatesDbApi.clearData()
    }

    override fun getAllData(): Flow<List<MyBalanceModel>> {
        return currencyRatesDbApi.getAllData()
    }

    override fun findMyBalanceCurrency(currency: String): MyBalanceModel? {
        return currencyRatesDbApi.findMyBalanceCurrency(currency)
    }

    override suspend fun exchangeBalance(
        amount: String,
        currency: String,
    ) {
        return currencyRatesDbApi.exchangeBalance(amount,currency)
    }
}