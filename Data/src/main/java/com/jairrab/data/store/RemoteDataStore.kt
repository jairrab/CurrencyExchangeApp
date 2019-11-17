package com.jairrab.data.store

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.repository.DataSourceRepository
import com.jairrab.data.repository.RemoteRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataStore @Inject constructor(
    private val remoteRepository: RemoteRepository
) : DataSourceRepository {

    override fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRateData> {
        return remoteRepository.getExchangeRate(inputCurrency, outputCurrencies)
    }

    override fun getCachedTimeStamp(source: String): Observable<Long> {
        throw IllegalStateException("Function not supported!")
    }

    override fun saveExchangeRates(exchangeRateData: ExchangeRateData): Completable {
        throw IllegalStateException("Function not supported!")
    }
}