package com.orka.myfinances.ui.managers

import com.orka.myfinances.models.Session
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.login.LoginScreenViewModel

sealed interface UiState {
    data object Initial : UiState
    data class Guest(val viewModel: LoginScreenViewModel) : UiState
    data class SignedIn(
        val session: Session,
        val viewModel: HomeScreenViewModel
    ) : UiState
}