package com.jairrab.presentation.state

sealed class ApiKeyState {

    class SetApiKeyVisibility(val isVisible: Boolean) : ApiKeyState()

    object ShowApiKeyReset : ApiKeyState()

}