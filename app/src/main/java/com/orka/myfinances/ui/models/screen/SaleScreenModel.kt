package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ClientCardModel
import com.orka.myfinances.ui.models.card.UserCardModel
import com.orka.myfinances.ui.models.item.Item
import kotlin.time.Instant

data class SaleScreenModel(
    val id: Id,
    val price: Int,
    val dateTime: Instant,
    val client: ClientCardModel,
    val user: UserCardModel,
    val clientId: Id,
    val items: List<Item>,
    val description: String? = null
)