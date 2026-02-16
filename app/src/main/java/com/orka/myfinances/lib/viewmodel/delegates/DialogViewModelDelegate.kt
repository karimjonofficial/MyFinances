package com.orka.myfinances.lib.viewmodel.delegates

import com.orka.myfinances.lib.viewmodel.DialogViewModel

interface DialogViewModelDelegate<T> : DialogViewModel<T> {
    fun setDialogState(value: T)
    fun updateDialogState(callback: (T) -> T)
}