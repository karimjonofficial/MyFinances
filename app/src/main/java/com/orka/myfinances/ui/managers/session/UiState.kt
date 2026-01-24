package com.orka.myfinances.ui.managers.session

import com.orka.myfinances.impl.ui.managers.NavigationManager
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel

sealed interface UiState {
    data object Initial : UiState
    data class Guest(val viewModel: LoginScreenViewModel) : UiState
    data class SignedIn(
        val session: Session,
        val navigationManager: NavigationManager
    ) : UiState
}