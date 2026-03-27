package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.ui.viewmodel.StateFul

abstract class StateFul<T>(
    initialState: T,
    logger: Logger
) : SingleStateViewModel<T>(
    initialState = initialState,
    logger = logger
), StateFul {
    abstract fun initialize()
}