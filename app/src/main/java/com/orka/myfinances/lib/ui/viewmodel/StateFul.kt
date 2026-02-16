package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.Manager
import com.orka.myfinances.lib.viewmodel.StateFul
import kotlinx.coroutines.delay

typealias IStateFul = StateFul

abstract class StateFul : Manager(), IStateFul {
    init {
        launch {
            delay(1000)
            initialize()
        }
    }
}