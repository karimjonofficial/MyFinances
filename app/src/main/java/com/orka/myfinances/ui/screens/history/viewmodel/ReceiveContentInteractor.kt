package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ReceiveContentInteractor : StateFul {
    fun select(receive: ReceiveUiModel)
}