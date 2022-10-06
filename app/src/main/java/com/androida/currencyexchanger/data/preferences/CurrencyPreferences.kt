package com.androida.currencyexchanger.data.preferences

import com.androida.currencyexchanger.core.custom.consts.CURRENCY_EXCHANGE_COUNT_KEY
import com.androida.currencyexchanger.data.preferences.manager.PreferencesManager
import javax.inject.Inject


class CurrencyPreferences @Inject constructor(
    private val preferencesManager: PreferencesManager
) : CurrencyPreferencesApi {

    override suspend fun saveConvertedCount(count: Int) {
        preferencesManager.saveConvertedCount(key = CURRENCY_EXCHANGE_COUNT_KEY, count)
    }

    override suspend fun getConvertedCount(): Int {
        return preferencesManager.getConvertedCount(key = CURRENCY_EXCHANGE_COUNT_KEY)
    }

}

