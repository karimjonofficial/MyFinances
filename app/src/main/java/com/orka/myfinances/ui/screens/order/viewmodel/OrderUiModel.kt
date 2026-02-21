package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.ui.screens.order.components.OrderCardModel

data class OrderUiModel(
    val order: Order,
    val model: OrderCardModel
)