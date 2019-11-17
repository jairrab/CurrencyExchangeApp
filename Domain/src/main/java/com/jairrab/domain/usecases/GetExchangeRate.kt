package com.jairrab.domain.usecases

import com.jairrab.domain.ObservableUseCase
import com.jairrab.domain.entities.ExchangeRate
import com.jairrab.domain.entities.ExchangeRateRequest
import com.jairrab.domain.executor.PostExecutionThread
import com.jairrab.domain.repository.DomainRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetExchangeRate @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val domainRepository: DomainRepository
) : ObservableUseCase<ExchangeRate, ExchangeRateRequest>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: ExchangeRateRequest?): Observable<ExchangeRate> {
        return domainRepository.getExchangeRate(
            inputCurrency = params?.inputCurrency ?: throw IllegalArgumentException(),
            outputCurrencies = params.outputCurrencies
        )
    }
}