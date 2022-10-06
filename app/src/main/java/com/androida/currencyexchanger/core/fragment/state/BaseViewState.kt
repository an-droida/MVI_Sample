package com.androida.currencyexchanger.core.fragment.state

interface BaseViewState<Data> {
    fun stateReducer(previousData: Data): Data = previousData
}
