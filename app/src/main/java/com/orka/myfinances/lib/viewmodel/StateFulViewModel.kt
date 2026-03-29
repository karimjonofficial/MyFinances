package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.viewmodel.StateFul

abstract class StateFulViewModel<T>(
    initialState: T,
    logger: Logger
) : SingleStateViewModel<T>(
    initialState = initialState,
    logger = logger
), StateFul