package com.jairrab.data

import com.jairrab.data.mapper.Mapper
import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.store.CacheDataStore
import com.jairrab.data.store.DataStore
import com.jairrab.data.store.RemoteDataStore
import com.jairrab.data.util.TimeUtils
import com.jairrab.domain.entities.ExchangeRate
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DataSourceRepositoryTest {

    private val remoteRepo = mock<RemoteDataStore>()
    private val localRepo = mock<CacheDataStore>()
    private val timeUtils = mock<TimeUtils>()

    private lateinit var dataStore: DataStore
    private lateinit var dataRepository: DataRepository
    private lateinit var mapper: Mapper

    @Before
    fun setUp() {
        dataStore = DataStore(localRepo, remoteRepo)
        mapper = Mapper()
        dataRepository = DataRepository(dataStore, timeUtils, mapper)
    }

    @Test
    fun getExchangeRate() {

        whenever(localRepo.getCachedTimeStamp("USD"))
            .thenReturn(Observable.just(123))

        whenever(timeUtils.isCacheValid(any()))
            .thenReturn(false)

        val exchangeRateData = makeExchangeRateData()

        whenever(remoteRepo.getExchangeRate(any(), eq(null)))
            .thenReturn(Observable.just(exchangeRateData))

        whenever(localRepo.getExchangeRate(any(), any()))
            .thenReturn(Observable.just(exchangeRateData))

        whenever(localRepo.saveExchangeRates(any()))
            .thenReturn(Completable.complete())

        val observer = dataRepository.getExchangeRate("USD", null).test()

        val value = observer.values().first()

        assertEquals(
            value, ExchangeRate(
                source = exchangeRateData.source,
                quotes = exchangeRateData.quotes,
                timestamp = exchangeRateData.timestamp
            )
        )
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
}
