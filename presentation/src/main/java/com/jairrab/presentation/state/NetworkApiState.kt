package com.jairrab.presentation.state

import com.jairrab.domain.entities.ExchangeRate

sealed class NetworkApiState {

    class ExchangeRateReceived(val exchangeRate: ExchangeRate?) : NetworkApiState()

    class CurrenciesRetrieved(val currencies: List<String>) : NetworkApiState()

    object ExchangeRateActionDone : NetworkApiState()

    object NetworkError : NetworkApiState()

    class ApiError(val message: String?) : NetworkApiState()

    class HasOtherError(val message: String?) : NetworkApiState()
}