package com.androida.currencyexchanger.data.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConvertCurrencyResponse(
    @SerialName("result")
    val result: Double?,
    @SerialName("success")
    val success: Boolean?
)