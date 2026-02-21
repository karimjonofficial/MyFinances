package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class SingleStateViewModel<T>(
    initialState: T,
    protected val logger: Logger
) : StateFul() {
    protected val state: MutableStateFlow<T> = MutableStateFlow(initialState)

    protected fun setState(value: T) {
        val old = state.value
        state.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    protected fun updateState(callback: (T) -> T) {
        state.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }
}