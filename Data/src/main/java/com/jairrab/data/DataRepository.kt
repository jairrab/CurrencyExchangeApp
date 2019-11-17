package com.jairrab.data

import com.jairrab.data.mapper.Mapper
import com.jairrab.data.store.CacheDataStore
import com.jairrab.data.store.DataSource.CACHE
import com.jairrab.data.store.DataSource.REMOTE
import com.jairrab.data.store.DataStore
import com.jairrab.data.util.TimeUtils
import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.domain.repository.DomainRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataStore: DataStore,
    private val timeUtils: TimeUtils,
    private val mapper: Mapper
) : DomainRepository {

    /**
     * Free CurrencyLayer API does not allow getting rates when source currency is
     * not USD. Two calls are made to get rates at USD and the rate between USD and
     * the input currency. The final rates are combined using a mapper function to multiply
     * the 2 rates
     */
    override fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRate> {

        return localRepo.getCachedTimeStamp("USD") //gets cached timestamp
            .onErrorReturnItem(0) //cache should return 0 if empty, or when there's error
            .map { dataStore.getDataStore(if (timeUtils.isCacheValid(it)) CACHE else REMOTE) }
            .flatMap { repo ->
                when (inputCurrency == "USD") {
                    true -> repo.getExchangeRate("USD", outputCurrencies)
                        .flatMap {
                            when (repo is CacheDataStore) {
                                true -> Observable.just(it)
                                else -> localRepo.saveExchangeRates(it).andThen(Observable.just(it))
                            }
                        }
                    else -> repo.getExchangeRate("USD", outputCurrencies)
                        .flatMap {
                            when (repo is CacheDataStore) {
                                true -> Observable.just(it)
                                else -> localRepo.saveExchangeRates(it).andThen(Observable.just(it))
                            }
                        }
                        .zipWith(
                            localRepo.getExchangeRate("USD", listOf(inputCurrency)),
                            BiFunction { t1, t2 ->
                                mapper.mapRatesToNonUsdCurrency(
                                    inputCurrency = inputCurrency,
                                    uSdExchangeData = t1,
                                    nonUsdExchangeData = t2
                                )
                            })
                }
            }
            .map { mapper.mapToExchangeRate(it) }
    }

    private val localRepo get() = dataStore.getDataStore(CACHE)
}