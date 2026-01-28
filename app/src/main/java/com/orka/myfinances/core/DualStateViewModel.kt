package com.orka.myfinances.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

abstract class DualStateViewModel<T1, T2>(
    initialState1: T1,
    initialState2: T2,
    protected val logger: Logger
) : StateFul() {
    protected val state1 = MutableStateFlow(initialState1)
    protected val state2 = MutableStateFlow(initialState2)

    init {
        launch {
            delay(100)
            initialize()
        }
    }

    protected fun setState1(state: T1) {
        state1.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State1 transition to ${state?.javaClass?.name}"
        )
    }

    protected fun setState2(state: T2) {
        state2.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State2 transition to ${state?.javaClass?.name}"
        )
    }
}