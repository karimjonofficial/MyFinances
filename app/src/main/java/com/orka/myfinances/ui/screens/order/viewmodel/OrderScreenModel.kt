package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.data.models.User
import com.orka.myfinances.ui.screens.clients.viewmodel.ClientModel

data class OrderScreenModel(
    val price: String,
    val completed: Boolean,
    val startDate: String,
    val endDate: String,
    val items: List<OrderItemModel>,
    val client: ClientModel,
    val user: User,
    val description: String?
)