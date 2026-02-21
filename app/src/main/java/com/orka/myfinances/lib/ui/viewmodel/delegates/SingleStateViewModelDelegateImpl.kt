package com.orka.myfinances.lib.ui.viewmodel.delegates

import com.orka.myfinances.core.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SingleStateViewModelDelegateImpl<T>(
    override val initialState: T,
    private val logger: Logger
) : SingleStateViewModelDelegate<T> {
    private val state = MutableStateFlow(initialState)
    override val uiState = state.asStateFlow()

    override fun setState(value: T) {
        val old = uiState.value
        state.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    override fun updateState(callback: (T) -> T) {
        state.update {
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