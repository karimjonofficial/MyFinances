package com.orka.myfinances.lib.ui.viewmodel

sealed interface State<TLoading, TSuccess, TFailure> {
    data class Initial<TLoading, TSuccess, TFailure>(val message: Unit = Unit) : State<TLoading, TSuccess, TFailure>
    data class Loading<TLoading, TSuccess, TFailure>(val message: TLoading) : State<TLoading, TSuccess, TFailure>
    data class Success<TLoading, TSuccess, TFailure>(val value: TSuccess) : State<TLoading, TSuccess, TFailure>
    data class Failure<TLoading, TSuccess, TFailure>(val error: TFailure) : State<TLoading, TSuccess, TFailure>
}