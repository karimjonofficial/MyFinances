package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class DualStateViewModel<P, S>(
    initialState1: P,
    initialState2: S,
    private val logger: Logger
) : StateFul() {
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

    protected fun updateState1(callback: (P) -> P) {
        state1.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }

    protected fun updateState2(callback: (S) -> S) {
        state2.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }
}