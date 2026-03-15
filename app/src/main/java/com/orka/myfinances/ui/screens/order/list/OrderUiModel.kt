package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.order.list.components.OrderCardModel

data class OrderUiModel(
    val id: Id,
    val model: OrderCardModel
)