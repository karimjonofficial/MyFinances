package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface OrdersScreenInteractor : StateFul, ChunkViewModel {
    fun select(order: OrderUiModel)
}