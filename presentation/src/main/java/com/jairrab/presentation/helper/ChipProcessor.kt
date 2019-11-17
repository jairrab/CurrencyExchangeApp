package com.jairrab.presentation.helper

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jairrab.presentation.event.Event
import com.jairrab.presentation.state.ChipViewState
import com.jairrab.presentation.utils.PreferenceKeys
import java.util.*
import javax.inject.Inject

class ChipProcessor @Inject constructor(
    private val preferences: SharedPreferences
) {

    private val chipQueue = LinkedList<String>()
    private val maxChipsQuantity = 5
    private var firstCurrency = ""

    fun initialize(
        currency: String,
        updateState: (Event<ChipViewState>) -> Unit
    ) {

        updateState(Event(ChipViewState.SetVisibility(chipQueue.isNotEmpty())))

        if (chipQueue.isNotEmpty()) {

            updateState(Event(ChipViewState.SetVisibility(true)))
            updateState(Event(ChipViewState.AddChips(chipQueue.toList())))

        } else {

            preferences.getStringSet(PreferenceKeys.RECENT_CURRENCIES, null)
                ?.toList()?.let {

                    chipQueue.addAll(it)

                    updateState(Event(ChipViewState.SetVisibility(true)))
                    updateState(Event(ChipViewState.AddChips(chipQueue.toList())))

                } ?: let {

                updateState(Event(ChipViewState.SetVisibility(false)))
                firstCurrency = currency
            }
        }
    }

    fun processChip(
        currency: String,
        updateState: (Event<ChipViewState>) -> Unit
    ) {

        if (!chipQueue.contains(currency)) {

            //when selecting currency first time, adds the initial currency to the chip list
            if (chipQueue.isEmpty()) {
                chipQueue.add(firstCurrency)
                updateState(Event(ChipViewState.AddChip(chipQueue.first)))
            }

            chipQueue.add(currency)

            if (chipQueue.size > maxChipsQuantity) {
                chipQueue.poll()?.let {
                    updateState(Event(ChipViewState.AddChip(currency)))
                    updateState(Event(ChipViewState.RemoveChip(it)))
                }
            } else {
                updateState(Event(ChipViewState.AddChip(currency)))
            }

        } else {

            chipQueue.remove(currency)
            updateState(Event(ChipViewState.RemoveChip(currency)))

            chipQueue.add(currency)
            updateState(Event(ChipViewState.AddChip(currency)))

        }

        updateState(Event(ChipViewState.SetVisibility(chipQueue.isNotEmpty())))

        preferences.edit { putStringSet(PreferenceKeys.RECENT_CURRENCIES, chipQueue.toSet()) }
    }
}