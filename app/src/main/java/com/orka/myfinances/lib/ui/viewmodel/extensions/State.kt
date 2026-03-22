package com.orka.myfinances.lib.ui.viewmodel.extensions

import com.orka.myfinances.lib.ui.viewmodel.State

fun <T> State<T>.isInitial(): Boolean {
    return this is State.Loading && this.value == null
}