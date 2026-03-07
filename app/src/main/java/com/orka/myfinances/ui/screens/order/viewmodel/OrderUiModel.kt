package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.order.components.OrderCardModel

data class OrderUiModel(
    val id: Id,
    val model: OrderCardModel
)
