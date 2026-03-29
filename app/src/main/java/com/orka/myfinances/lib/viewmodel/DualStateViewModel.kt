package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow

abstract class DualStateViewModel<P, S>(
    initialState1: P,
    initialState2: S,
    protected val logger: Logger
) : Manager() {
    protected val state1 = MutableStateFlow(initialState1)
    protected val state2 = MutableStateFlow(initialState2)

    protected fun setState1(value: P) {
        val old = state1.value
        state1.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    protected fun setState2(value: S) {
        val old = state2.value
        state2.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }
}