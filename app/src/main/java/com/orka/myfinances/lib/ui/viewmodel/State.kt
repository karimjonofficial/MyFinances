package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.lib.ui.models.Text

sealed interface State {
    data object Initial : State
    data class Loading(val message: Text) : State
    data class Success<out T>(val value: T) : State
    data class Failure(val error: Text) : State
}