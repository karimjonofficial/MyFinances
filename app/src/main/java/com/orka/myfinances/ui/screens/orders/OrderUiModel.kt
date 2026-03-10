package com.orka.myfinances.ui.screens.orders

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.orders.components.OrderCardModel

data class OrderUiModel(
    val id: Id,
    val model: OrderCardModel
)