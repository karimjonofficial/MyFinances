package com.orka.myfinances.lib.ui.viewmodel.delegates

import com.orka.myfinances.lib.ui.viewmodel.DialogViewModel

interface DialogViewModelDelegate<T> : DialogViewModel<T> {
    fun setDialogState(value: T)
    fun updateDialogState(callback: (T) -> T)
}