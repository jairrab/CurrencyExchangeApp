package com.jairrab.remote.service

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.remote.mapper.Mapper
import com.jairrab.remote.model.CurrencyLayerResponse
import io.reactivex.Observable
import javax.inject.Inject

class TestRetrofitResponse @Inject constructor(
    private val mapper: Mapper
) {

    fun getSingleCurrencyResponse(inputCurrency: String): Observable<ExchangeRateData> {

        return Observable.just(
            mapper.mapToExchangeRateData(
                CurrencyLayerResponse(
                    "privacy",
                    mapOf(
                        Pair("USDBBB", 1.5)
                    ),
                    inputCurrency,
                    true,
                    "terms",
                    12345678
                )
            )
        )
    }

    fun getAllCurrenciesResponse(inputCurrency: String): Observable<ExchangeRateData> {

        return Observable.just(
            mapper.mapToExchangeRateData(
                CurrencyLayerResponse(
                    "privacy",
                    mapOf(
                        Pair("USDUSD", 1.5),
                        Pair("USDAAA", 1.5),
                        Pair("USDBBB", 1.5),
                        Pair("USDCCC", 1.5),
                        Pair("USDDDD", 1.5),
                        Pair("USDEEE", 1.5),
                        Pair("USDFFF", 1.5),
                        Pair("USDGGG", 1.5),
                        Pair("USDHHH", 1.5),
                        Pair("USDIII", 1.5),
                        Pair("USDJJJ", 1.5),
                        Pair("USDKKK", 1.5),
                        Pair("USDLLL", 1.5),
                        Pair("USDMMM", 1.5),
                        Pair("USDNNN", 1.5),
                        Pair("USDOOO", 1.5),
                        Pair("USDPPP", 1.5),
                        Pair("USDQQQ", 1.5),
                        Pair("USDRRR", 1.5),
                        Pair("USDSSS", 1.5),
                        Pair("USDTTT", 1.5),
                        Pair("USDUUU", 1.5),
                        Pair("USDVVV", 1.5),
                        Pair("USDWWW", 1.5),
                        Pair("USDXXX", 1.5),
                        Pair("USDYYY", 1.5),
                        Pair("USDZZZ", 1.5),
                        Pair("USDAA1", 1.5),
                        Pair("USDBB1", 1.5),
                        Pair("USDCC1", 1.5),
                        Pair("USDDD1", 1.5),
                        Pair("USDEE1", 1.5),
                        Pair("USDFF1", 1.5),
                        Pair("USDGG1", 1.5),
                        Pair("USDHH1", 1.5),
                        Pair("USDII1", 1.5),
                        Pair("USDJJ1", 1.5),
                        Pair("USDKK1", 1.5),
                        Pair("USDLL1", 1.5),
                        Pair("USDMM1", 1.5),
                        Pair("USDNN1", 1.5),
                        Pair("USDOO1", 1.5),
                        Pair("USDPP1", 1.5),
                        Pair("USDQQ1", 1.5),
                        Pair("USDRR1", 1.5),
                        Pair("USDSS1", 1.5),
                        Pair("USDTT1", 1.5),
                        Pair("USDUU1", 1.5),
                        Pair("USDVV1", 1.5),
                        Pair("USDWW1", 1.5),
                        Pair("USDXX1", 1.5),
                        Pair("USDYY1", 1.5),
                        Pair("USDZZ1", 1.5)
                    ),
                    inputCurrency,
                    true,
                    "terms",
                    12345678
                )
            )
        )
    }
}