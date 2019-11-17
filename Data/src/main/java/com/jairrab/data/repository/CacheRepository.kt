package com.jairrab.data.repository

import com.jairrab.data.model.ExchangeRateData
import io.reactivex.Completable
import io.reactivex.Observable

interface CacheRepository {

    fun getCachedTimeStamp(source:String): Observable<Long>

    fun saveExchangeRates(exchangeRateData: ExchangeRateData): Completable

    fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRateData>
}