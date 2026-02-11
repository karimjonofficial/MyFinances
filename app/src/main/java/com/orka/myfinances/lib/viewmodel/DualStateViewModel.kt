package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface DualStateViewModel<P, S> {
    val state1: StateFlow<P>
    val state2: StateFlow<S>

    fun setState1(value: P)
    fun setState2(value: S)
    fun updateState1(callback: (P) -> P)
    fun updateState2(callback: (S) -> S)
}