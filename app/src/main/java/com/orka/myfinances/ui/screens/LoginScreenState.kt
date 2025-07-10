package com.orka.myfinances.ui.screens

sealed interface LoginScreenState {
    data object Initial : LoginScreenState
    data object Error : LoginScreenState
    data object Loading : LoginScreenState
}