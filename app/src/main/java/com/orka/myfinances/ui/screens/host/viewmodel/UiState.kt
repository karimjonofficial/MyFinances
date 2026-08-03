package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.lib.ui.state.FailureStatus

sealed interface UiState {
    data object Initial : UiState
    data object Guest : UiState
    data object Loading : UiState
    data class Failure(val type: FailureStatus = FailureStatus.Unspecified) : UiState
    data class NewUser(val credentials: Credentials, val companyId: Id) : UiState
    data class SignedIn(val session: Session) : UiState
}