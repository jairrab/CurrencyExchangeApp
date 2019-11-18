package com.jairrab.presentation.helper

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jairrab.presentation.event.Event
import com.jairrab.presentation.state.HistoryViewState
import com.jairrab.presentation.utils.PreferenceKeys
import java.util.*
import javax.inject.Inject

class HistoryProcessor @Inject constructor(
    private val preferences: SharedPreferences
) {

    private val historyQueue = LinkedList<String>()
    private val maxHistorySize = 5
    private var firstCurrency = ""

    fun initialize(
        currency: String,
        updateState: (Event<HistoryViewState>) -> Unit
    ) {

        updateState(Event(HistoryViewState.SetVisibility(historyQueue.isNotEmpty())))

        if (historyQueue.isNotEmpty()) {

            updateState(Event(HistoryViewState.SetVisibility(true)))
            updateState(Event(HistoryViewState.AddHistories(historyQueue.toList())))

        } else {

            preferences.getStringSet(PreferenceKeys.RECENT_CURRENCIES, null)
                ?.toList()?.let {

                    historyQueue.addAll(it)

                    updateState(Event(HistoryViewState.SetVisibility(true)))
                    updateState(Event(HistoryViewState.AddHistories(historyQueue.toList())))

                } ?: let {

                updateState(Event(HistoryViewState.SetVisibility(false)))
                firstCurrency = currency
            }
        }
    }

    fun processHistory(
        currency: String,
        updateState: (Event<HistoryViewState>) -> Unit
    ) {

        if (!historyQueue.contains(currency)) {

            //when selecting currency first time, adds the initial currency to the history list
            if (historyQueue.isEmpty()) {
                historyQueue.add(firstCurrency)
                updateState(Event(HistoryViewState.AddHistoryView(historyQueue.first)))
            }

            historyQueue.add(currency)

            if (historyQueue.size > maxHistorySize) {
                historyQueue.poll()?.let {
                    updateState(Event(HistoryViewState.AddHistoryView(currency)))
                    updateState(Event(HistoryViewState.RemoveHistoryView(it)))
                }
            } else {
                updateState(Event(HistoryViewState.AddHistoryView(currency)))
            }

        } else {

            historyQueue.remove(currency)
            updateState(Event(HistoryViewState.RemoveHistoryView(currency)))

            historyQueue.add(currency)
            updateState(Event(HistoryViewState.AddHistoryView(currency)))

        }

        updateState(Event(HistoryViewState.SetVisibility(historyQueue.isNotEmpty())))

        preferences.edit { putStringSet(PreferenceKeys.RECENT_CURRENCIES, historyQueue.toSet()) }
    }
}