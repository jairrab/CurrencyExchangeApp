package com.jairrab.remote

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.util.TimeUtils
import com.jairrab.remote.mapper.Mapper
import com.jairrab.remote.model.CurrencyLayerResponse
import com.jairrab.remote.service.RetrofitFactory
import com.jairrab.remote.service.RetrofitService
import com.jairrab.remote.service.TestRetrofitResponse
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class RetrofitClientTest {

    private val service = mock<RetrofitService>()
    private val timeUtils = mock<TimeUtils>()
    private val testRetrofitResponse = mock<TestRetrofitResponse>()

    private lateinit var client: RetrofitClient

    @Before
    fun setUp() {
        val mapper = Mapper(timeUtils)
        client = RetrofitClient(mapper, testRetrofitResponse, service)
    }

    @Test
    fun getExchangeRate() {

        val response = CurrencyLayerResponse(
            privacy = Rndom.string(),
            quotes = makeQuotes(),
            source = Rndom.string(),
            success = true,
            terms = Rndom.string(),
            timestamp = 123
        )

        whenever(timeUtils.currentTime).thenReturn(123)

        whenever(service.getExchangeRate(
            apiKey = RetrofitFactory.apiKey,
            inputCurrency = "USD"
        )).thenReturn(Observable.just(response))

        val observer = client.getExchangeRate("USD", null).test()

        val exchangeRateData = ExchangeRateData(
            source = response.source ?: "",
            quotes = response.quotes,
            timestamp = 123
        )

        observer.assertValue(exchangeRateData)
    }

    private fun makeQuotes(): HashMap<String, Double> {

        val a = HashMap<String, Double>(0)
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()
        a[Rndom.string()] = Rndom.double()

        return a
    }
}