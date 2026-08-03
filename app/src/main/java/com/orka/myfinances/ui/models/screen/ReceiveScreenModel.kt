package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.ui.models.card.UserCardModel
import com.orka.myfinances.ui.models.item.ReceiveItemModel
import kotlin.time.Instant

data class ReceiveScreenModel(
    val user: UserCardModel,
    val price: Int,
    val dateTime: Instant,
    val items: List<ReceiveItemModel>,
    val description: String? = null
)