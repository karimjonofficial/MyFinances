package com.orka.myfinances.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class SingleStateViewModel<T>(
    initialState: T,
    protected val logger: Logger
) : StateFul() {
    protected val state = MutableStateFlow(initialState)

    init {
        launch {
            delay(100)
            initialize()
        }
    }

    protected fun updateState(
        callback: (T) -> T
    ) {
        state.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }

    protected fun setState(state: T) {
        this.state.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State transition to ${state?.javaClass?.name}"
        )
    }
}