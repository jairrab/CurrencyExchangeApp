package com.jairrab.data.model

data class ExchangeRateData(
    val source: String,
    val quotes: Map<String, Double>?,
    val timestamp: Long
)