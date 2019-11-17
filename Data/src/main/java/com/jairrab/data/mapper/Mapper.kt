package com.jairrab.data.mapper

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.domain.entities.ExchangeRate
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapToExchangeRate(data: ExchangeRateData): ExchangeRate {
        return ExchangeRate(
            quotes = data.quotes,
            source = data.source,
            timestamp = data.timestamp
        )
    }

    fun mapRatesToNonUsdCurrency(
        inputCurrency: String,
        uSdExchangeData: ExchangeRateData,
        nonUsdExchangeData: ExchangeRateData
    ): ExchangeRateData {

        return ExchangeRateData(
            source = inputCurrency,
            quotes = uSdExchangeData.quotes?.toList()
                ?.map {
                    val currency = it.first

                    val rateVsUsd = it.second

                    val inputCurrencyFactor = nonUsdExchangeData.quotes
                                                  ?.entries?.iterator()
                                                  ?.next()?.value?: 0.0

                    val newRate = rateVsUsd / inputCurrencyFactor

                    Pair(currency, newRate)
                }
                ?.toMap(),
            timestamp = maxOf(uSdExchangeData.timestamp, nonUsdExchangeData.timestamp)
        )
    }
}