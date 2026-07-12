package com.orka.myfinances.application.manager.ui

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Session

sealed interface UiState {
    data object Initial : UiState
    data object Guest : UiState
    data object Loading : UiState
    data class Failure(val type: FailureType = FailureType.UnSpecified) : UiState
    data class NewUser(val credentials: Credentials, val companyId: Id) : UiState
    data class SignedIn(val session: Session) : UiState
}