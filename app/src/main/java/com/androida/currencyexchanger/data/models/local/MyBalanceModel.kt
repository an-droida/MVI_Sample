package com.androida.currencyexchanger.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_balance")
data class MyBalanceModel(
    @PrimaryKey(autoGenerate = false)
    val currencyId: Int,
    val currency: String,
    val balance: String
)