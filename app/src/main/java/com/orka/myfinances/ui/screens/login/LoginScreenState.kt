package com.orka.myfinances.ui.screens.login

import com.orka.myfinances.lib.ui.models.UiText

sealed interface LoginScreenState {
    data object Initial : LoginScreenState
    data object Loading : LoginScreenState

    data class Error(
        val message: UiText,
        val serverError: Boolean = false
    ) : LoginScreenState
}