package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface SingleStateViewModel<T> : StateFul  {
    val uiState: StateFlow<T>
}