package com.orka.myfinances.lib.viewmodel.list

import com.orka.myfinances.lib.ui.models.UiText

sealed interface State {
    data object Initial : State
    data class Loading(val message: UiText) : State
    data class Success<out T>(val value: T) : State
    data class Failure(val error: UiText) : State
}