package com.orka.myfinances.lib.ui.viewmodel.delegates

import com.orka.myfinances.core.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogViewModelDelegateImpl<T>(
    initialState: T,
    private val logger: Logger
) : DialogViewModelDelegate<T> {
    private val _dialogState = MutableStateFlow(initialState)
    override val dialogState = _dialogState.asStateFlow()

    override fun setDialogState(value: T) {
        val old = _dialogState.value
        _dialogState.value = value
        logger.log(
            tag = this.javaClass.name,
            message = "State transition from ${old?.javaClass?.name} to ${value?.javaClass?.name}"
        )
    }

    override fun updateDialogState(callback: (T) -> T) {
        _dialogState.update {
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