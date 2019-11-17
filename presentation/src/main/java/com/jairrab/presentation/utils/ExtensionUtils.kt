package com.jairrab.presentation.utils

fun Double.getProperlyFormattedDecimal(): String {
    return when {
        this == 0.0    -> "0.00"
        this < 0.00001 -> String.format("%.8f", this)
        this < 0.0001  -> String.format("%.7f", this)
        this < 0.001   -> String.format("%.6f", this)
        this < 0.01    -> String.format("%.5f", this)
        this < 0.1     -> String.format("%.4f", this)
        this < 1       -> String.format("%.3f", this)
        this < 1000    -> String.format("%.2f", this)
        this < 10000   -> String.format("%.1f", this)
        else           -> String.format("%.0f", this)
    }
}