package com.jairrab.data.store

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.repository.CacheRepository
import com.jairrab.data.repository.DataSourceRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CacheDataStore @Inject constructor(
    private val cacheRepository: CacheRepository
) : DataSourceRepository {

    override fun getCachedTimeStamp(source: String): Observable<Long> {
        return cacheRepository.getCachedTimeStamp(source)
    }

    override fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRateData> {
        return cacheRepository.getExchangeRate(inputCurrency, outputCurrencies)
    }

    override fun saveExchangeRates(exchangeRateData: ExchangeRateData): Completable {
        return cacheRepository.saveExchangeRates(exchangeRateData)
    }
}