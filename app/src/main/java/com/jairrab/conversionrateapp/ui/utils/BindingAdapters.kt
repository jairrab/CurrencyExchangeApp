package com.jairrab.conversionrateapp.ui.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:showView")
    fun showView(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:cancelVisibility")
    fun cancelVisibility(view: View, amount: String) {
        view.visibility = if (amount != "")
            View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:setCurrency")
    fun setCurrency(view: TextView, currency: String) {
        val flagEmoji = FlagMap.emoji[currency]?:"\uD83C\uDFC1"
        view.text = String.format("%s %s", currency, flagEmoji)
    }

    @JvmStatic
    @BindingAdapter("app:setCurrencyMultiLine")
    fun setCurrencyMultiLine(view: TextView, currency: String) {
        val symbol = CurrencyMap.getSymbol(currency)
        val flagEmoji = FlagMap.emoji[currency]?:"\uD83C\uDFC1"

        view.text = if (symbol == currency || symbol == "") {
            String.format("%s\n%s", currency, flagEmoji)
        } else {
            String.format("%s(%s)\n%s", currency, symbol, flagEmoji)
        }
    }

    @JvmStatic
    @BindingAdapter("app:setRate")
    fun setRate(view: TextView, rate: Double) {
        view.text = when {
            rate == 0.0    -> "0.00"
            rate < 0.00001 -> String.format("%.8f", rate)
            rate < 0.0001  -> String.format("%.7f", rate)
            rate < 0.001   -> String.format("%.6f", rate)
            rate < 0.01    -> String.format("%.5f", rate)
            rate < 0.1     -> String.format("%.4f", rate)
            rate < 1       -> String.format("%.3f", rate)
            rate < 1000    -> String.format("%.2f", rate)
            rate < 10000   -> String.format("%.1f", rate)
            else           -> String.format("%.0f", rate)
        }
    }
}