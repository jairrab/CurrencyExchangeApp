package com.jairrab.domain.usecases

import com.jairrab.domain.Rndom
import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.domain.entities.ExchangeRateRequest
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.domain.repository.DomainRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetExchangeRateTest {

    private lateinit var getExchangeRate: GetExchangeRate

    private val postExecutionThread = mock<PostExecutionThread>()
    private val domainRepository = mock<DomainRepository>()


    @Before
    fun setUp() {
        getExchangeRate = GetExchangeRate(postExecutionThread, domainRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getExchangeRate() {

        val exchangeRate = makeExchangeRate()

        whenever(domainRepository.getExchangeRate("USD"))
            .thenReturn(Observable.just(exchangeRate))

        val observer = getExchangeRate.buildUseCaseObservable(
            ExchangeRateRequest("USD")
        ).test()

        val value = observer.values().first()

        assertEquals(value, exchangeRate)
    }

    private fun makeExchangeRate(): ExchangeRate {
        return ExchangeRate(
            source = Rndom.string(),
            quotes = null,
            timestamp = Rndom.long()
        )
    }
}