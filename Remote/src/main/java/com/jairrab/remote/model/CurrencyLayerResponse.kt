package com.jairrab.remote.model

data class CurrencyLayerResponse(
    val privacy: String?,
    val quotes: Map<String, Double>?,
    val source: String?,
    val success: Boolean,
    val terms: String?,
    val timestamp: Int,
    val error: Map<String, String>? = null
)