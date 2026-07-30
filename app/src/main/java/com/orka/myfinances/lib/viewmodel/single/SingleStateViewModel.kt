package com.orka.myfinances.lib.viewmodel.single

import com.orka.myfinances.lib.viewmodel.manager.Manager
import com.orka.myfinances.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class SingleStateViewModel<T>(
    initialState: T,
    logger: Logger
) : Manager(logger) {
    protected val state: MutableStateFlow<T> = MutableStateFlow(initialState)

    protected fun setState(value: T) {
        val old = state.value
        state.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
        logger.log(
            tag = this.javaClass.name,
            message = "New state: $value"
        )
    }

    protected fun updateState(callback: (T) -> T) {
        state.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            logger.log(
                tag = this.javaClass.name,
                message = "New state: $s"
            )
            s
        }
    }
}