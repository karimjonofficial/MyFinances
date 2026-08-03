package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ClientCardModel
import com.orka.myfinances.ui.models.card.UserCardModel
import kotlin.time.Instant

data class DebtScreenModel(
    val completed: Boolean,
    val price: Int,
    val startDate: Instant,
    val endDateTime: Instant?,
    val notified: Boolean,
    val client: ClientCardModel,
    val isOverdue: Boolean,
    val id: Id,
    val user: UserCardModel,
    val clientId: Id,
    val description: String? = null
)