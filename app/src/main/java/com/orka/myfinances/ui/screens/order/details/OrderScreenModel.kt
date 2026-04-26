package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.components.UserCardModel
import com.orka.myfinances.ui.screens.order.details.OrderItemModel

data class OrderScreenModel(
    val price: String,
    val completed: Boolean,
    val startDate: String,
    val endDate: String?,
    val items: List<OrderItemModel>,
    val client: ClientCardModel,
    val clientId: Id,
    val user: UserCardModel,
    val userId: Id,
    val description: String?
)