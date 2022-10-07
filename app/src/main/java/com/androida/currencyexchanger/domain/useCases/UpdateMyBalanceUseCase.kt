package com.androida.currencyexchanger.domain.useCases

import com.androida.currencyexchanger.core.custom.enums.CurrencyRates
import com.androida.currencyexchanger.data.models.local.MyBalanceModel
import com.androida.currencyexchanger.domain.repositories.CurrencyPreferenceRepository
import com.androida.currencyexchanger.domain.repositories.MyBalanceRepository
import javax.inject.Inject

class UpdateMyBalanceUseCase @Inject constructor(
    private val myBalanceRepo: MyBalanceRepository,
    private val preferencesRepo: CurrencyPreferenceRepository,
) : BaseUseCase<Boolean, UpdateMyBalanceUseCase.Params>() {


    data class Params(
        val baseCurrencyFrom: String,
        val baseCurrencyTo: String,
        val sell: Double,
        val receive: Double,
    )

    override suspend fun run(params: Params): Boolean {
        val currentBalance = myBalanceRepo.findMyBalanceCurrency(params.baseCurrencyFrom)
        if (currentBalance != null && currentBalance.balance.toDouble() >= params.sell) {
            val amount = (currentBalance.balance.toDouble() - params.sell)
            val roundedAmount = String.format("%.2f", amount)
            myBalanceRepo.exchangeBalance(
                amount = roundedAmount,
                currency = params.baseCurrencyFrom
            )
            val convertedBalance =
                myBalanceRepo.findMyBalanceCurrency(params.baseCurrencyTo)
            if (convertedBalance != null) {
                val newBalance = convertedBalance.balance.toDouble() + params.receive.toDouble()
                val roundedNewBalance = String.format("%.2f", newBalance)
                myBalanceRepo.saveOrUpdateData(
                    MyBalanceModel(
                        balance = roundedNewBalance,
                        currency = convertedBalance.currency,
                        currencyId = CurrencyRates.valueOf(convertedBalance.currency).currencyId
                    )
                )
            } else {
                myBalanceRepo.saveOrUpdateData(
                    MyBalanceModel(
                        balance = params.receive.toString(),
                        currency = params.baseCurrencyTo,
                        currencyId = CurrencyRates.valueOf(params.baseCurrencyTo).currencyId
                    )
                )
            }
            val count = preferencesRepo.getConvertedCount()
            preferencesRepo.saveConvertedCount(count + 1)
            return true
        } else {
            return false
        }
    }
}