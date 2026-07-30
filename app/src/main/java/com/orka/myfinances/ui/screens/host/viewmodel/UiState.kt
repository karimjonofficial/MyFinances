package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.lib.ui.state.FailureType

sealed interface UiState {
    data object Initial : UiState
    data object Guest : UiState
    data object Loading : UiState
    data class Failure(val type: FailureType = FailureType.Unspecified) : UiState
    data class NewUser(val credentials: Credentials, val companyId: Id) : UiState
    data class SignedIn(val session: Session) : UiState
}