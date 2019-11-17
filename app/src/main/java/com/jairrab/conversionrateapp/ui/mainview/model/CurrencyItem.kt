package com.jairrab.conversionrateapp.ui.mainview.model

data class CurrencyItem(
    val currency: String,
    val rate: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyItem

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