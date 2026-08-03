package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ClientCardModel
import com.orka.myfinances.ui.models.card.UserCardModel
import com.orka.myfinances.ui.models.item.OrderItemModel
import kotlin.time.Instant

data class OrderScreenModel(
    val price: Int,
    val completed: Boolean,
    val startDate: Instant,
    val endDate: Instant?,
    val items: List<OrderItemModel>,
    val client: ClientCardModel,
    val clientId: Id,
    val user: UserCardModel,
    val userId: Id,
    val description: String?
)