package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.application.manager.NavigationManager
import com.orka.myfinances.application.viewmodels.office.SelectOfficeScreenViewModel
import com.orka.myfinances.application.viewmodels.login.LoginScreenViewModel

sealed interface UiState {
    data object Initial : UiState
    data class Guest(val viewModel: LoginScreenViewModel) : UiState
    data class Failure(val message: UiText) : UiState
    data class NewUser(
        val credentials: Credentials,
        val viewModel: SelectOfficeScreenViewModel
    ) : UiState

    data class SignedIn(
        val session: Session,
        val navigationManager: NavigationManager,
        val factory: Factory
    ) : UiState
}