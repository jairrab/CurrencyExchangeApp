package com.jairrab.presentation.helper

import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.domain.entities.ExchangeRateRequest
import com.jairrab.domain.entities.ServerApiError
import com.jairrab.domain.usecases.GetExchangeRate
import com.jairrab.presentation.event.Event
import com.jairrab.presentation.mapper.Mapper
import com.jairrab.presentation.state.NetworkApiState
import com.jairrab.presentation.state.NetworkApiState.*
import com.jairrab.presentation.utils.getProperlyFormattedDecimal
import io.reactivex.observers.DisposableObserver
import java.net.UnknownHostException
import javax.inject.Inject

class ExchangeRateProcessor @Inject constructor(
    private val getExchangeRate: GetExchangeRate,
    private val mapper: Mapper
) {
    private var lastAmount = 1.0
    private var lastExchangeRate: ExchangeRate? = null

    fun getExchangeRate(
        inputCurrency: String,
        amount: Double,
        updateState: (Event<NetworkApiState>) -> Unit
    ) {
        lastAmount = amount

        getExchangeRate.execute(object : DisposableObserver<ExchangeRate>() {

            override fun onComplete() {
                updateState(Event(ExchangeRateActionDone))
            }

            override fun onNext(exchangeRate: ExchangeRate) {
                updateExchangeRate(exchangeRate, lastAmount, updateState)
            }

            override fun onError(e: Throwable) {
                //structuring repository errors into states for handling (or non-handling)
                when (e) {
                    is UnknownHostException -> updateState(Event(NetworkError))
                    is ServerApiError       -> updateState(Event(ApiError(e.message)))
                    else                    -> updateState(Event(HasOtherError(e.message)))
                }
            }

        }, ExchangeRateRequest(inputCurrency, null))
    }

    fun getExchangeRate(
        inputCurrency: String,
        updateState: (Event<NetworkApiState>) -> Unit
    ) = getExchangeRate(inputCurrency, lastAmount, updateState)

    fun updateAmount(
        amount: Double,
        updateState: (Event<NetworkApiState>) -> Unit
    ) {
        updateExchangeRate(lastExchangeRate, amount, updateState)
    }

    fun getAmountText(): String {
        return lastAmount.getProperlyFormattedDecimal()
    }

    fun getCurrencies(updateState: (Event<NetworkApiState>) -> Unit) {
        val currencies = mapper.mapToCurrencies(lastExchangeRate)
        updateState(Event(CurrenciesRetrieved(currencies)))
    }

    private fun updateExchangeRate(
        exchangeRate: ExchangeRate?,
        amount: Double,
        updateState: (Event<NetworkApiState>) -> Unit
    ) {
        lastAmount = amount
        lastExchangeRate = exchangeRate

        val rates = mapper.mapToConvertedExchangeRate(exchangeRate, amount)

        updateState(Event(ExchangeRateReceived(rates)))
    }
}