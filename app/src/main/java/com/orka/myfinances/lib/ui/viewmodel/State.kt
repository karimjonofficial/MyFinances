package com.orka.myfinances.lib.ui.viewmodel

typealias N = Nothing

sealed interface State<out L, out S, out F> {
    data object Initial : State<N, N, N>
    data class Loading<T>(val message: T) : State<T, N, N>
    data class Success<T>(val value: T) : State<N, T, N>
    data class Failure<T>(val error: T) : State<N, N, T>
}