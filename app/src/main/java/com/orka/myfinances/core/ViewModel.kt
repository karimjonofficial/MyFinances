package com.orka.myfinances.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

abstract class ViewModel<T>(
    initialState: T,
    defaultCoroutineContext: CoroutineContext,
    protected val logger: Logger
) : Manager(defaultCoroutineContext) {
    protected val state = MutableStateFlow(initialState)

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

    protected suspend fun setStateAsync(state: T) {
        this.state.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State transition to ${state?.javaClass?.name}"
        )
        yield()
    }
}