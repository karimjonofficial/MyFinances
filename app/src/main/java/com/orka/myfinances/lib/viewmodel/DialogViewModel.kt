package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface DialogViewModel<T> : StateFul {
    val dialogState: StateFlow<T>
}