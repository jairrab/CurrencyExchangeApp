package com.jairrab.presentation.state

sealed class HistoryViewState {

    class SetVisibility(val isVisible:Boolean) : HistoryViewState()

    class AddHistoryView(val currency: String) : HistoryViewState()

    class AddHistories(val currencies: List<String>) : HistoryViewState()

    class RemoveHistoryView(val currency: String) : HistoryViewState()

}