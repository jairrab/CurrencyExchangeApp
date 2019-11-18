package com.jairrab.presentation.model

data class CurrencyRate(
    val currency: String,
    val rate: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyRate

        if (currency != other.currency) return false
        if (rate != other.rate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + rate.hashCode()
        return result
    }
}