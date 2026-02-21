package com.orka.myfinances.lib.ui.viewmodel.delegates

import com.orka.myfinances.lib.ui.viewmodel.DualStateViewModel

interface DualStateViewModelDelegate<P, S> : DualStateViewModel<P, S> {
    fun setState1(value: P)
    fun setState2(value: S)
    fun updateState1(callback: (P) -> P)
    fun updateState2(callback: (S) -> S)
}