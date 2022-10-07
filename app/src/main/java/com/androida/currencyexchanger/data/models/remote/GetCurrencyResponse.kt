package com.androida.currencyexchanger.data.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCurrencyResponse(
    @SerialName("base")
    val base: String?,
    @SerialName("date")
    val date: String?,
    @SerialName("rates")
    val rates: RatesModel?,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("timestamp")
    val timestamp: Int?
)