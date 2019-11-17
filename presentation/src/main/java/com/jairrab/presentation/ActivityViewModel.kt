package com.jairrab.presentation

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jairrab.presentation.event.Event
import com.jairrab.presentation.state.ApiKeyState
import com.jairrab.presentation.state.ApiKeyState.SetApiKeyVisibility
import com.jairrab.presentation.state.ApiKeyState.ShowApiKeyReset
import com.jairrab.presentation.state.StatusMessageState
import com.jairrab.presentation.state.StatusMessageState.ShowMessage
import com.jairrab.presentation.utils.PreferenceKeys
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val app: Application,
    private val preferences: SharedPreferences
) : AndroidViewModel(app) {

    private val _progressViewVisibility: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val progressViewVisibility: LiveData<Event<Boolean>> = _progressViewVisibility

    private val _currencies: MutableLiveData<List<String>> = MutableLiveData()
    val currencies: LiveData<List<String>> = _currencies

    private val _currencySelection: MutableLiveData<Event<String>> = MutableLiveData()
    val currencySelection: LiveData<Event<String>> = _currencySelection

    private val _statusMessageState: MutableLiveData<Event<StatusMessageState>> = MutableLiveData()
    val statusMessageState: LiveData<Event<StatusMessageState>> = _statusMessageState

    private val _apiKeyState: MutableLiveData<Event<ApiKeyState>> = MutableLiveData()
    val apiKeyState: LiveData<Event<ApiKeyState>> = _apiKeyState

    fun startApp() {
        if (preferences.getBoolean(PreferenceKeys.NEW_INSTALL, true)) {
            preferences.edit { putBoolean(PreferenceKeys.NEW_INSTALL, false) }
            _progressViewVisibility.value = Event(true)
            _statusMessageState.value = Event(ShowMessage(getString(R.string.first_time_setup)))
        } else {
            _progressViewVisibility.value = Event(true)
        }
    }

    fun stopLoadingStatus() {
        _progressViewVisibility.value = Event(false)
        _statusMessageState.value = Event(StatusMessageState.HideMessage)
    }

    fun showNetworkErrorMessage() {
        _progressViewVisibility.value = Event(false)
        _statusMessageState.value = Event(ShowMessage(getString(R.string.network_error)))
    }

    fun showApiKeyError(message: String?) {
        val error = String.format(getString(R.string.error_message), message)
        _progressViewVisibility.value = Event(false)
        _statusMessageState.value = Event(ShowMessage(error))
        _apiKeyState.value = Event(SetApiKeyVisibility(true))
    }

    fun showError(message: String?) {
        val error = String.format(getString(R.string.error_message), message)
        _progressViewVisibility.value = Event(false)
        _statusMessageState.value = Event(ShowMessage(error))
    }

    fun onCurrencySelected(currency: String) {
        _currencySelection.value = Event(currency)
    }

    fun updateCurrencies(currencies: List<String>) {
        _currencies.postValue(currencies)
    }

    fun onResetApiKey() {
        _apiKeyState.value = Event(ShowApiKeyReset)
    }

    fun updateApiKey(key: String) {
        if (key == "") return
        preferences.edit { putString(PreferenceKeys.API_KEY, key) }
        _statusMessageState.value = Event(ShowMessage(getString(R.string.restart_to_use_key)))
    }

    private fun getString(resId: Int) = app.getString(resId)
}