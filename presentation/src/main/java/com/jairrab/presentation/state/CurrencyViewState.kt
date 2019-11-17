package com.jairrab.presentation.state

sealed class CurrencyViewState {

    class UpdateText(val currency: String) : CurrencyViewState()

    object OnCurrencySelection : CurrencyViewState()
}