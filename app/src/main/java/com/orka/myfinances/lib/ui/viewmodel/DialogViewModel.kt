package com.orka.myfinances.lib.ui.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface DialogViewModel<T> : StateFul {
    val dialogState: StateFlow<T>
}