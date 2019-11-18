package com.jairrab.presentation.state

import com.jairrab.presentation.model.CurrencyRate

sealed class NetworkApiState {

    class ExchangeRateReceived(val currencyRates: List<CurrencyRate>?) : NetworkApiState()

    class CurrenciesRetrieved(val currencies: List<String>) : NetworkApiState()

    object ExchangeRateActionDone : NetworkApiState()

    object NetworkError : NetworkApiState()

    class ApiError(val message: String?) : NetworkApiState()

    class HasOtherError(val message: String?) : NetworkApiState()
}