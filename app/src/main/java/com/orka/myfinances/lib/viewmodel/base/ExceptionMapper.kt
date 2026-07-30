package com.orka.myfinances.lib.viewmodel.base

import com.orka.myfinances.lib.ui.state.FailureType
import com.orka.myfinances.lib.ui.state.State

fun interface ExceptionMapper<T> {
    suspend fun map(oldState: State<T>?, e: Exception): State<T>

    class Default<T> : ExceptionMapper<T> {
        override suspend fun map(oldState: State<T>?, e: Exception): State<T> {
            return State.Failure(value = oldState?.value, type = FailureType.Exception(e.message.toString()))
        }
    }
}