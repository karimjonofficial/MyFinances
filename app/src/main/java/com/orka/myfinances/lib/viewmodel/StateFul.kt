package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.StateFul
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