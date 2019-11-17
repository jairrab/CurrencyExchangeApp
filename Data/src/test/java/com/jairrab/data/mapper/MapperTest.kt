package com.jairrab.data.mapper

import com.jairrab.data.Rndom
import com.jairrab.data.model.ExchangeRateData
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    private val mapper = Mapper()

    @Test
    fun mapToExchangeRate() {

        val d1 = makeExchangeRateData()
        val d2 = mapper.mapToExchangeRate(d1)

        assertEquals(d2.source, d1.source)
        assertEquals(d2.timestamp, d1.timestamp)
        assertEquals(d2.quotes, d1.quotes)
    }

    private fun makeExchangeRateData(): ExchangeRateData {
        return ExchangeRateData(
            source = Rndom.string(),
            quotes = makeQuotes(),
            timestamp = Rndom.long()
        )
    }

    private fun makeQuotes(): HashMap<String, Double> {

        val a = HashMap<String, Double>(0)
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()

        return a
    }

    @Test
    fun mapRatesToNonUsdCurrency() {

        val e1 = ExchangeRateData(
            source = "USD",
            quotes = object : HashMap<String, Double>() {
                init {
                    put("USD", 1.0)
                    put("PHP", 2.0)
                    put("EUR", 3.0)
                }
            },
            timestamp = 123
        )

        val e2 = ExchangeRateData(
            source = "USD",
            quotes = object : HashMap<String, Double>() {
                init {
                    put("PHP", 2.0)
                }
            },
            timestamp = 345
        )

        val expectedOutput = ExchangeRateData(
            source = "USD",
            quotes = object : HashMap<String, Double>() {
                init {
                    put("USD", 0.5)
                    put("PHP", 1.0)
                    put("EUR", 1.5)
                }
            },
            timestamp = 345
        )

        val output = mapper.mapRatesToNonUsdCurrency(
            inputCurrency = "USD",
            uSdExchangeData = e1,
            nonUsdExchangeData = e2
        )

        assertEquals(expectedOutput.source, output.source)
        assertEquals(expectedOutput.timestamp, output.timestamp)
        assertEquals(expectedOutput.quotes, output.quotes)
    }
}