package com.jairrab.presentation.state

sealed class StatusMessageState {

    object HideMessage : StatusMessageState()

    class ShowMessage(val message: String?) : StatusMessageState()

}