package com.androida.currencyexchanger.domain.repositories

import com.androida.currencyexchanger.data.preferences.CurrencyPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyPreferenceRepositoryImpl @Inject constructor(
    private val currencyPreference: CurrencyPreferences
) : CurrencyPreferenceRepository {

    override suspend fun saveConvertedCount(count: Int) {
        currencyPreference.saveConvertedCount(count)
    }

    override suspend fun getConvertedCount(): Int {
        return currencyPreference.getConvertedCount()
    }

}