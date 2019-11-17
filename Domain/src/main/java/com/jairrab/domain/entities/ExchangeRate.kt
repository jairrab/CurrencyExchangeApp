package com.jairrab.domain.entities

data class ExchangeRate(
    val source: String,
    val quotes: Map<String, Double>?,
    val timestamp: Long
)