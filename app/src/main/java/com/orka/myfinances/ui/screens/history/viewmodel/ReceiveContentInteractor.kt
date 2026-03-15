package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ReceiveContentInteractor : StateFul, MapViewModel<ReceiveUiModel> {
    fun select(receive: ReceiveUiModel)
}