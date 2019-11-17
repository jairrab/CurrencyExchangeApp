package com.jairrab.remote.service

import com.jairrab.remote.model.CurrencyLayerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {


    @GET("live?format=1")
    fun getExchangeRate(
        @Query("access_key") apiKey: String,
        @Query("from") inputCurrency: String
    ): Observable<CurrencyLayerResponse>

    @GET("live?format=1")
    fun getExchangeRate(
        @Query("access_key") apiKey: String,
        @Query("from") inputCurrency: String,
        @Query("currencies") outputCurrencies: String
    ): Observable<CurrencyLayerResponse>
}