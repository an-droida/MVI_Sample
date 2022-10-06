package com.androida.currencyexchanger.data.preferences.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class PreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {

    override suspend fun saveConvertedCount(key: String, count: Int) {
        dataStore.edit {
            it[intPreferencesKey(key)] = count
        }
    }

    override suspend fun getConvertedCount(key: String): Int {
        return dataStore.data.firstOrNull()?.get(intPreferencesKey(key)) ?: 0
    }

}