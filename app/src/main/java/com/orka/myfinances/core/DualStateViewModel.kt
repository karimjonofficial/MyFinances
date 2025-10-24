package com.orka.myfinances.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.yield
import kotlin.coroutines.CoroutineContext

abstract class DualStateViewModel<T1, T2>(
    initialState1: T1,
    initialState2: T2,
    defaultCoroutineContext: CoroutineContext,
    protected val logger: Logger
) : Manager(defaultCoroutineContext) {

    protected val state1 = MutableStateFlow(initialState1)
    protected val state2 = MutableStateFlow(initialState2)

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

    protected suspend fun setState1Async(state: T1) {
        state1.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State1 transition to ${state?.javaClass?.name}"
        )
        yield()
    }

    protected suspend fun setState2Async(state: T2) {
        state2.value = state
        logger.log(
            tag = this.javaClass.name,
            message = "State2 transition to ${state?.javaClass?.name}"
        )
        yield()
    }

    protected fun updateState1(update: (currentState: T1) -> T1) {
        state1.update {
            val s = update(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State1 transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }

    protected fun updateState2(update: (currentState: T2) -> T2) {
        state2.update {
            val s = update(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State2 transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }
}