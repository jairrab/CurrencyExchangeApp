package com.jairrab.data.repository

import com.jairrab.data.model.ExchangeRateData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource

interface DataSourceRepository {

    fun getCachedTimeStamp(source: String): Observable<Long>

    fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>? = null
    ): Observable<ExchangeRateData>

    fun saveExchangeRates(exchangeRateData: ExchangeRateData): Completable
}