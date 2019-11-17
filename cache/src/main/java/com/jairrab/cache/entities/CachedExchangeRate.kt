package com.jairrab.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jairrab.cache.db.tables.ExchangeRateTable

@Entity(tableName = ExchangeRateTable.TABLE)
data class CachedExchangeRate(

    @PrimaryKey
    @ColumnInfo(name = ExchangeRateTable.SOURCE)
    val source: String,

    @ColumnInfo(name = ExchangeRateTable.QUOTES)
    val quotes: String,

    @ColumnInfo(name = ExchangeRateTable.TIMESTAMP)
    val timestamp: Long
)