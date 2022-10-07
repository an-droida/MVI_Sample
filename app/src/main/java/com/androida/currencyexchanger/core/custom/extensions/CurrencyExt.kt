package com.androida.currencyexchanger.core.custom.extensions

import com.androida.currencyexchanger.core.custom.consts.COMMISSION_FEE


fun Double.calculateCommissionFee(convertCount: Int): Double {
    val commissionFee: Double =
        if (convertCount > 5) {
            val fee = (this * COMMISSION_FEE)
            String.format("%.3f", fee).toDouble()
        } else {
            0.0
        }
    return commissionFee
}