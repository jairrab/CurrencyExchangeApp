package com.jairrab.presentation.state

sealed class ChipViewState {

    class SetVisibility(val isVisible:Boolean) : ChipViewState()

    class AddChip(val currency: String) : ChipViewState()

    class AddChips(val currencies: List<String>) : ChipViewState()

    class RemoveChip(val currency: String) : ChipViewState()

}