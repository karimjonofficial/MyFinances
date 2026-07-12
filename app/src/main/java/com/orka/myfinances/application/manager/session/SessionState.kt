package com.orka.myfinances.application.manager.session

sealed interface SessionState {
    data object Loading : SessionState
    data object Failure : SessionState
    data object Guest : SessionState
    data object NewUser : SessionState
    data object SignedIn : SessionState
}