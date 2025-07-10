package com.orka.myfinances.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

abstract class ViewModel<T>(
    initialState: T,
    context: CoroutineContext,
    protected val logger: Logger
) : Manager(context) {
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
}