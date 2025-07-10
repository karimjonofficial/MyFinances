package com.orka.myfinances

import com.orka.myfinances.models.Session
import com.orka.myfinances.ui.screens.LoginScreenViewModel

sealed interface UiState {
    data object Initial : UiState
    data class Guest(val viewModel: LoginScreenViewModel) : UiState
    data class SignedIn(val session: Session) : UiState
}