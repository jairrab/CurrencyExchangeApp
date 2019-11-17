package com.jairrab.domain.entities

data class ExchangeRateRequest(
    val inputCurrency: String,
    val outputCurrencies: List<String>? = null
)