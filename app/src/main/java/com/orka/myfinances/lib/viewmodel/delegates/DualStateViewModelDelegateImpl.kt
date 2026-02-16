package com.orka.myfinances.lib.viewmodel.delegates

import com.orka.myfinances.core.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DualStateViewModelDelegateImpl<P, S>(
    initialState1: P,
    initialState2: S,
    private val logger: Logger
) : DualStateViewModelDelegate<P, S> {
    private val _state1 = MutableStateFlow(initialState1)
    private val _state2 = MutableStateFlow(initialState2)
    override val state1 = _state1.asStateFlow()
    override val state2 = _state2.asStateFlow()

    override fun setState1(value: P) {
        val old = _state1.value
        _state1.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    override fun setState2(value: S) {
        val old = _state2.value
        _state2.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    override fun updateState1(callback: (P) -> P) {
        _state1.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }

    override fun updateState2(callback: (S) -> S) {
        _state2.update {
            val s = callback(it)
            logger.log(
                tag = this.javaClass.name,
                message = "State transition from ${it?.javaClass?.name} to ${s?.javaClass?.name}"
            )
            s
        }
    }

    override fun initialize() {}
}