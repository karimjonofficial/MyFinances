package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface DialogViewModel<T> {
    val dialogState: StateFlow<T>
    fun setDialogState(value: T)
    fun updateDialogState(callback: (T) -> T)
}