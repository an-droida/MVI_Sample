package com.androida.currencyexchanger.core.fragment.utils

import org.json.JSONObject
import retrofit2.Response

fun Response<*>.getErrorMessage(): String {
    val body = errorBody()
    val defaultMessage = "Something went wrong, Code: ${code()}"
    return if (body != null) {
        val messageJson = JSONObject(body.string())
        when {
            messageJson.has("message") -> messageJson["message"].toString()
            messageJson.has("errors") -> {
                messageJson.getJSONObject("errors").keys().asSequence().map {
                    messageJson.getJSONObject("errors").getJSONArray(it).join(", ")
                }.joinToString().replace("\"", "")
            }
            else -> {
                defaultMessage
            }
        }
    } else {
        defaultMessage
    }
}