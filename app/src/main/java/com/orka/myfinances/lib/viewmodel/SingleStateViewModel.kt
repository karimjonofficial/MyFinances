package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface SingleStateViewModel<T> {
    val uiState: StateFlow<T>
}

interface SingleStateViewModelDelegate<T> : SingleStateViewModel<T> {
    val initialState: T

    fun setState(value: T)
    fun updateState(callback: (T) -> T)
}