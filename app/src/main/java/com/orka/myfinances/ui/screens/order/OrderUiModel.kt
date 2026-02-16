package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.data.models.order.Order

data class OrderUiModel(
    val order: Order,
    val model: OrderCardModel
)