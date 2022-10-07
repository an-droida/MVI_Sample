package com.androida.currencyexchanger.core.activity.handlers

import com.androida.currencyexchanger.core.fragment.utils.exceptions.ApiException
import com.androida.currencyexchanger.core.fragment.utils.getErrorMessage
import retrofit2.Response

fun <T> Response<T>.checkResponseWithData(): T {
    val body = body()
    return when {
        isSuccessful -> {
            body!!
        }
        else -> {
            // TODO can handle error codes here
            throw ApiException(getErrorMessage())
        }
    }
}