package com.androida.currencyexchanger.core.fragment.action

interface BaseAction<Data> {

    fun stateReducer(previousData: Data): Data = previousData

}
