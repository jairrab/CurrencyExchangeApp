package com.jairrab.data.repository

import com.jairrab.data.model.ExchangeRateData
import io.reactivex.Observable

interface RemoteRepository {

    fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>? = null
    ): Observable<ExchangeRateData>
}