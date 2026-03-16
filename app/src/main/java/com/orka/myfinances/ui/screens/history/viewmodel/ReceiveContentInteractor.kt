package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ReceiveContentInteractor : StateFul, ChunkViewModel<ReceiveUiModel> {
    fun select(receive: ReceiveUiModel)
}