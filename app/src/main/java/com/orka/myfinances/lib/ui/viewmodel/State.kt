package com.orka.myfinances.lib.ui.viewmodel

sealed interface State<TLoading, TSuccess, TFailure> {
    data class Initial<TLoading, TSuccess, TFailure>(val message: Unit) : State<TLoading, TSuccess, TFailure>
    data class Loading<TLoading, TSuccess, TFailure>(val message: TLoading) : State<TLoading, TSuccess, TFailure>
    data class Success<TLoading, TSuccess, TFailure>(val value: TSuccess) : State<TLoading, TSuccess, TFailure>
    data class Failure<TLoading, TSuccess, TFailure>(val error: TFailure) : State<TLoading, TSuccess, TFailure>

    companion object {
        fun <TLoading, TSuccess, TFailure> initial(): Initial<TLoading, TSuccess, TFailure> {
            return Initial(Unit)
        }

        fun <TLoading, TSuccess, TFailure> loading(info: TLoading): Loading<TLoading, TSuccess, TFailure> {
            return Loading(info)
        }

        fun <TLoading, TSuccess, TFailure> success(value: TSuccess): Success<TLoading, TSuccess, TFailure> {
            return Success(value)
        }

        fun <TLoading, TSuccess, TFailure> failure(message: TFailure): Failure<TLoading, TSuccess, TFailure> {
            return Failure(message)
        }
    }
}