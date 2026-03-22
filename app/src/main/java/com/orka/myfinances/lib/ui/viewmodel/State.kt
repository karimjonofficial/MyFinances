package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.lib.ui.models.UiText

sealed class State<T>(open val value: T?) {
    data class Loading<T>(val message: UiText, override val value: T? = null) : State<T>(value)
    data class Success<T>(override val value: T) : State<T>(value)
    data class Failure<T>(val error: UiText, override val value: T? = null) : State<T>(value)
}