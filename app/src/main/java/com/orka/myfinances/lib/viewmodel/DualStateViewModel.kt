package com.orka.myfinances.lib.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface DualStateViewModel<P, S> : StateFul  {
    val state1: StateFlow<P>
    val state2: StateFlow<S>
}