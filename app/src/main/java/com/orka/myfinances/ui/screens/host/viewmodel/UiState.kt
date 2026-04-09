package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.application.manager.NavigationManager
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.screens.host.LoginScreenViewModelFactory
import com.orka.myfinances.ui.screens.host.SelectOfficeScreenViewModelFactory

sealed interface UiState {
    data object Initial : UiState
    data class Guest(val factory: LoginScreenViewModelFactory) : UiState
    data object Loading : UiState
    data class Failure(val message: UiText) : UiState

    data class NewUser(
        val credentials: Credentials,
        val companyId: Id,
        val factory: SelectOfficeScreenViewModelFactory
    ) : UiState

    data class SignedIn(
        val session: Session,
        val navigationManager: NavigationManager,
        val factory: Factory
    ) : UiState
}