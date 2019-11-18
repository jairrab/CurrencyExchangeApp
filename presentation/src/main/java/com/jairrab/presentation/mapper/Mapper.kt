package com.jairrab.presentation.mapper

import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.presentation.model.CurrencyRate
import java.util.*
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapToCurrencies(exchangeRate: ExchangeRate?): List<String> {

        return exchangeRate?.quotes?.keys?.toList() ?: ArrayList()
    }


    fun mapToConvertedExchangeRate(exchangeRate: ExchangeRate?, amount: Double): ExchangeRate? {

        return exchangeRate?.let { rate ->

            val amountMap = rate.quotes?.toList()?.map { it ->

                val currency = it.first

                val convertedAmount = it.second * amount

                Pair(currency, convertedAmount)

            }?.toMap()

            ExchangeRate(exchangeRate.source, amountMap, exchangeRate.timestamp)
        }
    }

    fun mapToCurrencyRates(exchangeRate: ExchangeRate?): List<CurrencyRate>? {

        return exchangeRate?.quotes?.map {
            CurrencyRate(it.key, it.value)
        }
    }

}