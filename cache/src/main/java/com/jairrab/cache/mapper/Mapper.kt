package com.jairrab.cache.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jairrab.cache.entities.CachedExchangeRate
import com.jairrab.data.model.ExchangeRateData
import javax.inject.Inject


class Mapper @Inject constructor() {

    fun mapToCachedExchangeRate(exchangeRateData: ExchangeRateData): CachedExchangeRate {
        return CachedExchangeRate(
            exchangeRateData.source,
            convertToString(exchangeRateData.quotes),
            exchangeRateData.timestamp
        )
    }

    fun mapToExchangeRateData(cachedExchangeRate: CachedExchangeRate): ExchangeRateData {
        return ExchangeRateData(
            cachedExchangeRate.source,
            convertToMap(cachedExchangeRate.quotes),
            cachedExchangeRate.timestamp
        )
    }

    fun mapToSourceCurrency(
        rateData: ExchangeRateData,
        outputCurrencies: List<String>
    ): ExchangeRateData {
        return ExchangeRateData(
            rateData.source,
            rateData.quotes?.filter { it.key == outputCurrencies[0] },
            rateData.timestamp
        )
    }

    private fun convertToString(map: Map<String, Double>?): String {
        return Gson().toJson(map)
    }

    private fun convertToMap(json: String): Map<String, Double> {
        val typeOfHashMap = object : TypeToken<Map<String, Double>>() {}.type

        return Gson().fromJson(json, typeOfHashMap)
    }
}