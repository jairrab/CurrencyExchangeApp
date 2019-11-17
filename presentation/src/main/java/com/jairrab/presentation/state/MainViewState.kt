package com.jairrab.presentation.state

import com.jairrab.domain.entities.ExchangeRate

sealed class MainViewState {

    class ExchangeRateReceived(val exchangeRate: ExchangeRate?) : MainViewState()

    class CurrenciesRetrieved(val currencies: List<String>) : MainViewState()

    object ExchangeRateActionDone : MainViewState()

    object NetworkError : MainViewState()

    class ApiError(val message: String?) : MainViewState()

    class HasOtherError(val message: String?) : MainViewState()
}