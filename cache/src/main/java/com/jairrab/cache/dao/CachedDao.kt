package com.jairrab.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jairrab.cache.db.tables.ExchangeRateTable
import com.jairrab.cache.entities.CachedExchangeRate
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class CachedDao {

    @Query("SELECT exchange_rate_timestamp FROM ${ExchangeRateTable.TABLE} WHERE exchange_rate_source = :source")
    abstract fun getCachedTimeStamp(source:String): Single<List<Long>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveExchangeRates(exchangeRate: CachedExchangeRate): Completable

    @Query("SELECT * FROM ${ExchangeRateTable.TABLE} WHERE exchange_rate_source = :inputCurrency")
    abstract fun getExchangeRate(inputCurrency: String): Single<CachedExchangeRate>


}