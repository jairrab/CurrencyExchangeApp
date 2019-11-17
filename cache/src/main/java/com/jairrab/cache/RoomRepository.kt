package com.jairrab.cache

import com.jairrab.cache.db.AppDatabase
import com.jairrab.cache.mapper.Mapper
import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.repository.CacheRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val database: AppDatabase,
    private val mapper: Mapper
) : CacheRepository {

    override fun getCachedTimeStamp(source: String): Observable<Long> {

        return database.cachedDao().getCachedTimeStamp(source)
            .map { if (it.isEmpty()) 0 else it[0] }
            .doOnSuccess { println("^^ Cache: getting timestamp") }
            .toObservable()
    }

    override fun saveExchangeRates(exchangeRateData: ExchangeRateData): Completable {

        return database.cachedDao()
            .saveExchangeRates(mapper.mapToCachedExchangeRate(exchangeRateData))
            .doOnComplete { println("^^ Cache: Saving exchange rate") }
    }

    override fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRateData> {

        return outputCurrencies?.let {

            database.cachedDao().getExchangeRate(inputCurrency)
                .doOnSuccess{ println("^^ Cache: Getting rate for $outputCurrencies")}
                .map { mapper.mapToExchangeRateData(it) }
                .map { mapper.mapToSourceCurrency(it, outputCurrencies) }
                .toObservable()

        } ?: let {

            database.cachedDao().getExchangeRate(inputCurrency)
                .doOnSuccess{ println("^^ Cache: Getting rate for all currencies")}
                .map { mapper.mapToExchangeRateData(it) }
                .toObservable()
        }
    }
}