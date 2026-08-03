package com.orka.myfinances.lib.ui.state

sealed class State<T>(open val value: T?) {
    data class Loading<T>(val status: LoadingStatus = LoadingStatus.Unspecified, override val value: T? = null) : State<T>(value)
    data class Success<T>(override val value: T) : State<T>(value)
    data class Failure<T>(val status: FailureStatus = FailureStatus.Unspecified, override val value: T? = null) : State<T>(value)
}