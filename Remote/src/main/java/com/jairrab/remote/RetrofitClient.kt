package com.jairrab.remote

import com.jairrab.data.model.ExchangeRateData
import com.jairrab.data.repository.RemoteRepository
import com.jairrab.data.state.DataApiError
import com.jairrab.remote.RetrofitClient.RetrofitResponse.*
import com.jairrab.remote.mapper.Mapper
import com.jairrab.remote.model.CurrencyLayerResponse
import com.jairrab.remote.service.RetrofitFactory.apiKey
import com.jairrab.remote.service.RetrofitService
import com.jairrab.remote.service.TestRetrofitResponse
import io.reactivex.Observable
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val mapper: Mapper,
    private val testRetrofitResponse: TestRetrofitResponse,
    private val retrofitService: RetrofitService
) : RemoteRepository {

    enum class RetrofitResponse { NORMAL, TEST_RESPONSE, API_ERROR }

    private val retrofitResponse = NORMAL

    @Suppress("ConstantConditionIf")
    override fun getExchangeRate(
        inputCurrency: String,
        outputCurrencies: List<String>?
    ): Observable<ExchangeRateData> {

        return outputCurrencies?.let { list ->

            when (retrofitResponse) {
                TEST_RESPONSE -> testRetrofitResponse.getSingleCurrencyResponse(inputCurrency)
                API_ERROR     -> Observable.error(DataApiError("Test error message"))
                NORMAL        -> retrofitService
                    .getExchangeRate(
                        apiKey = apiKey,
                        inputCurrency = inputCurrency,
                        outputCurrencies = mapper.mapListToQueryParameter(list)
                    )
                    .doOnNext { println("^^ Remote: Getting rate for $outputCurrencies") }
                    .flatMap {
                        it.error?.get("info")?.run {
                            Observable.error<CurrencyLayerResponse>(DataApiError(this))
                        } ?: run {
                            Observable.just(it)
                        }
                    }
                    .map { mapper.mapToExchangeRateData(it) }
            }


        } ?: let {

            when (retrofitResponse) {
                TEST_RESPONSE -> testRetrofitResponse.getAllCurrenciesResponse(inputCurrency)
                API_ERROR     -> Observable.error(DataApiError("Test error message"))
                NORMAL        -> retrofitService
                    .getExchangeRate(
                        apiKey = apiKey,
                        inputCurrency = inputCurrency
                    )
                    .doOnNext { println("^^ Remote: Getting rate for all currencies") }
                    .flatMap {
                        it.error?.get("info")?.run {
                            Observable.error<CurrencyLayerResponse>(DataApiError(this))
                        } ?: run {
                            Observable.just(it)
                        }
                    }
                    .map { mapper.mapToExchangeRateData(it) }
            }
        }
    }
}