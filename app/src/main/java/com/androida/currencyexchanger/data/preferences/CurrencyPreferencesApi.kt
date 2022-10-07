package com.androida.currencyexchanger.data.preferences

interface CurrencyPreferencesApi {

    suspend fun saveConvertedCount(count:Int)

    suspend fun getConvertedCount():Int

}