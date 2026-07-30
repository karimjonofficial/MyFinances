package com.orka.myfinances.lib.ui.state

sealed class State<T>(open val value: T?) {
    data class Loading<T>(val type: LoadingType = LoadingType.Unspecified, override val value: T? = null) : State<T>(value)
    data class Success<T>(override val value: T) : State<T>(value)
    data class Failure<T>(val type: FailureType = FailureType.Unspecified, override val value: T? = null) : State<T>(value)
}