package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.lib.ui.viewmodel.MapViewModel

interface OrdersScreenInteractor : MapViewModel<OrderUiModel> {
    fun select(order: OrderUiModel)
}