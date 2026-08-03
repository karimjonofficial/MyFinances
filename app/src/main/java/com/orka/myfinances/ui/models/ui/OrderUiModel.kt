package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.OrderCardModel

data class OrderUiModel(
    val id: Id,
    val model: OrderCardModel
)