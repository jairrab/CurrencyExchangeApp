package com.jairrab.domain.repository

import com.jairrab.domain.entities.*
import io.reactivex.Observable

interface DomainRepository {

    fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>? = null
    ): Observable<ExchangeRate>
}