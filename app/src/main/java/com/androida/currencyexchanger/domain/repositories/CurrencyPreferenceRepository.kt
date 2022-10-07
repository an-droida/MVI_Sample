package com.androida.currencyexchanger.domain.repositories

interface CurrencyPreferenceRepository {

    suspend fun saveConvertedCount(count: Int)

    suspend fun getConvertedCount(): Int

}