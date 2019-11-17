package com.jairrab.cache.mapper

import com.google.gson.Gson
import com.jairrab.cache.Rndom
import com.jairrab.cache.entities.CachedExchangeRate
import com.jairrab.data.model.ExchangeRateData
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val mapper = Mapper()

    @Test
    fun mapToCachedExchangeRate() {

        val d1 = ExchangeRateData(
            source = Rndom.string(),
            quotes = makeQuotes(),
            timestamp = Rndom.long()
        )

        val d2 = mapper.mapToCachedExchangeRate(d1)

        assertEquals(d2.source, d1.source)
        assertEquals(d2.quotes, Gson().toJson(d1.quotes))
        assertEquals(d2.timestamp, d1.timestamp)
    }

    @Test
    fun mapToExchangeRateData() {

        val quotes = makeQuotes()

        val d1 = CachedExchangeRate(
            source = Rndom.string(),
            quotes = Gson().toJson(quotes),
            timestamp = Rndom.long()
        )

        val d2 = mapper.mapToExchangeRateData(d1)

        assertEquals(d2.source, d1.source)
        assertEquals(d2.quotes, quotes)
        assertEquals(d2.timestamp, d1.timestamp)
    }

    @Test
    fun mapToSourceCurrency() {

        val quotes = makeQuotes()
        val double = Rndom.double()

        quotes["USD"] = double

        val expectedQuote = HashMap<String, Double>()
        expectedQuote["USD"] = double

        val d1 = ExchangeRateData(
            source = Rndom.string(),
            quotes = quotes,
            timestamp = Rndom.long()
        )

        val d2 = mapper.mapToSourceCurrency(d1, listOf("USD"))

        assertEquals(d2.source, d1.source)
        assertEquals(d2.quotes, expectedQuote)
        assertEquals(d2.timestamp, d1.timestamp)
    }

    private fun makeQuotes(): HashMap<String, Double> {

        val a = HashMap<String, Double>(0)
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()

        return a
    }
}