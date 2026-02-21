package com.orka.myfinances.lib.ui.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface SingleStateViewModel<T> : StateFul  {
    val uiState: StateFlow<T>
}