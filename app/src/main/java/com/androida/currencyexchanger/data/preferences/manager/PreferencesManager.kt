package com.androida.currencyexchanger.data.preferences.manager

interface PreferencesManager {

    suspend fun saveConvertedCount(key: String, count: Int)

    suspend fun getConvertedCount(key: String): Int

}