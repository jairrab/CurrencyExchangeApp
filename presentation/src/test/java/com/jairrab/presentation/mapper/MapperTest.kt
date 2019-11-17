package com.jairrab.presentation.mapper

import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.presentation.Rndom
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val mapper = Mapper()

    @Test
    fun mapToCurrencies() {

        val key1 = Rndom.string()
        val key2 = Rndom.string()
        val key3 = Rndom.string()

        val a = HashMap<String, Double>(0)
        a[key1] = Rndom.double()
        a[key2] = Rndom.double()
        a[key3] = Rndom.double()

        val d1 = ExchangeRate(
            source = Rndom.string(),
            quotes = a,
            timestamp = Rndom.long()
        )

        val d2 = mapper.mapToCurrencies(d1).sorted()

        val expected = listOf(key1, key2, key3).sorted()

        assertEquals(expected, d2)
    }

    @Test
    fun mapToConvertedExchangeRate() {

        val key1 = Rndom.string()
        val key2 = Rndom.string()
        val key3 = Rndom.string()

        val value1 = Rndom.double()
        val value2 = Rndom.double()
        val value3 = Rndom.double()

        val quote = HashMap<String, Double>(0)
        quote[key1] = value1
        quote[key2] = value2
        quote[key3] = value3

        val newQuote = HashMap<String, Double>(0)
        newQuote[key1] = value1 * 5.0
        newQuote[key2] = value2 * 5.0
        newQuote[key3] = value3 * 5.0

        val d1 = ExchangeRate(
            source = Rndom.string(),
            quotes = quote,
            timestamp = Rndom.long()
        )

        val expected = ExchangeRate(
            source = d1.source,
            quotes = newQuote,
            timestamp = d1.timestamp
        )

        val d2 = mapper.mapToConvertedExchangeRate(d1, 5.0)

        assertEquals(d2, expected)
    }
}