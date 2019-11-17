package com.jairrab.presentation

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jairrab.domain.usecases.GetExchangeRate
import com.jairrab.presentation.event.Event
import com.jairrab.presentation.helper.ChipProcessor
import com.jairrab.presentation.helper.ExchangeRateProcessor
import com.jairrab.presentation.state.ChipViewState
import com.jairrab.presentation.state.CurrencyViewState
import com.jairrab.presentation.state.CurrencyViewState.OnCurrencySelection
import com.jairrab.presentation.state.CurrencyViewState.UpdateText
import com.jairrab.presentation.state.NetworkApiState
import com.jairrab.presentation.utils.PreferenceKeys
import com.jairrab.presentation.utils.getProperlyFormattedDecimal
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getExchangeRate: GetExchangeRate,
    private val preferences: SharedPreferences,
    private val exchangeRateProcessor: ExchangeRateProcessor,
    private val chipProcessor: ChipProcessor
) : ViewModel() {

    private val _networkApiState = MutableLiveData<Event<NetworkApiState>>()
    val networkApiState: LiveData<Event<NetworkApiState>> = _networkApiState

    private val _amountViewObservable = MutableLiveData<String>()
    val amountViewObservable: LiveData<String> = _amountViewObservable

    private val _currencyViewObservable = MutableLiveData<Event<CurrencyViewState>>()
    val currencyViewObservable: LiveData<Event<CurrencyViewState>> = _currencyViewObservable

    private val _cancelViewObservable = MutableLiveData<Boolean>()
    val cancelViewObservable: LiveData<Boolean> = _cancelViewObservable

    private val _chipViewObservable = MutableLiveData<Event<ChipViewState>>()
    val chipViewObservable: LiveData<Event<ChipViewState>> = _chipViewObservable

    var currency = ""

    private val debounceTime = 250L
    private var debounceJob: Job? = null
    private var skipAmountTwoWayBindingListener = false

    fun initialize() {
        skipAmountTwoWayBindingListener = true

        //initialize last amount
        val amount = preferences.getString(PreferenceKeys.LAST_AMOUNT, "1.0").let {
            if (it?.isEmpty() == true) 1.0 else it?.toDouble() ?: 1.0
        }

        _cancelViewObservable.postValue(amount != 1.0)

        //initialize currency setting
        currency = preferences.getString(PreferenceKeys.LAST_CURRENCY, null) ?: "USD"

        //initializes last currency chips
        chipProcessor.initialize(currency) { _chipViewObservable.value = it }

        //gets initial rates
        exchangeRateProcessor.getExchangeRate(currency, amount) { _networkApiState.value = it }
    }

    //region Two-Way Data Binding for Amount Input Entry
    fun getAmountText(): String {
        return exchangeRateProcessor.getAmountText()
    }

    fun setAmountText(text: String) {
        if (!skipAmountTwoWayBindingListener) {

            debounceJob?.cancel()

            //debounces the user input to avoid unnecessary
            //calculations during fast user typing
            debounceJob = viewModelScope.launch {
                delay(debounceTime)

                preferences.edit { putString(PreferenceKeys.LAST_AMOUNT, text) }

                val amount = if (text.isEmpty()) 1.0 else text.toDouble()
                exchangeRateProcessor.updateAmount(amount) { _networkApiState.value = it }

                _cancelViewObservable.postValue(amount != 1.0)
            }
        } else {
            skipAmountTwoWayBindingListener = false
        }
    }
    //endregion

    fun onCurrencyButtonClicked() {
        exchangeRateProcessor.getCurrencies { _networkApiState.value = it }
        _currencyViewObservable.value = Event(OnCurrencySelection)
    }

    fun onCurrencySelected(currency: String) {
        preferences.edit { putString(PreferenceKeys.LAST_CURRENCY, currency) }

        _currencyViewObservable.postValue(Event(UpdateText(currency)))

        chipProcessor.processChip(currency) { _chipViewObservable.value = it }
        exchangeRateProcessor.getExchangeRate(currency) { _networkApiState.value = it }
    }

    fun onCurrencyCellSelected(currency: String, amount: Double) {
        skipAmountTwoWayBindingListener = true

        preferences.edit { putString(PreferenceKeys.LAST_CURRENCY, currency) }
        _currencyViewObservable.postValue(Event(UpdateText(currency)))

        val newAmountText = amount.getProperlyFormattedDecimal()
        preferences.edit { putString(PreferenceKeys.LAST_AMOUNT, newAmountText) }

        _amountViewObservable.postValue(newAmountText)
        _cancelViewObservable.postValue(amount != 1.0)

        chipProcessor.processChip(currency) { _chipViewObservable.value = it }
        exchangeRateProcessor.getExchangeRate(currency, amount) { _networkApiState.value = it }
    }

    fun onCancelClicked() {
        skipAmountTwoWayBindingListener = true

        val amount = 1.0

        preferences.edit { putString(PreferenceKeys.LAST_AMOUNT, amount.toString()) }

        _amountViewObservable.postValue(amount.getProperlyFormattedDecimal())
        _cancelViewObservable.postValue(false)

        exchangeRateProcessor.updateAmount(amount) { _networkApiState.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        getExchangeRate.dispose()
    }
}