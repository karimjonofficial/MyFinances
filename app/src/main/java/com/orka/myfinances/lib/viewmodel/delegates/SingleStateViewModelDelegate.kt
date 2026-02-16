package com.orka.myfinances.lib.viewmodel.delegates

import com.orka.myfinances.lib.viewmodel.SingleStateViewModel

interface SingleStateViewModelDelegate<T> : SingleStateViewModel<T> {
    val initialState: T

    fun setState(value: T)
    fun updateState(callback: (T) -> T)
}