package com.jairrab.remote.mapper

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.util.TimeUtils
import com.jairrab.remote.model.CurrencyLayerResponse
import javax.inject.Inject

class Mapper @Inject constructor(
    private val timeUtils: TimeUtils
) {

    /**
     * Maps the retrofit response into a set of sorted quotes without the leading source currency
     * in the map keys (e.g. USDEUR should be returned as EUR)
     * @param [data] input retrofit response
     * @return Sorted Quotes
     */
    fun mapToExchangeRateData(data: CurrencyLayerResponse): ExchangeRateData {

        return ExchangeRateData(
            source = data.source ?: "",
            quotes = getSortedMap(data.quotes, data.source),
            timestamp = timeUtils.currentTime
        )
    }

    fun mapListToQueryParameter(list: List<String>):String {
        return list.toString().let {
            it.substring(1, it.length - 1)
        }
    }

    private fun getSortedMap(quotes: Map<String, Double>?, source: String?): Map<String, Double>? {

        if (source == null) return null

        if (quotes == null) return null

        return quotes
            .map { Pair(it.key.substringAfter(source), it.value) }
            .toMap()
            .toSortedMap()
    }
}