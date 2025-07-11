package com.orka.myfinances.ui.screens.login

sealed interface LoginScreenState {
    data object Initial : LoginScreenState
    data object Error : LoginScreenState
    data object Loading : LoginScreenState
}