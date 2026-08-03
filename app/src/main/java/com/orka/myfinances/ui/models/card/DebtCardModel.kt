package com.orka.myfinances.ui.models.card

import kotlin.time.Instant

data class DebtCardModel(
    val name: String,
    val price: Int,
    val dateTime: Instant,
    val description: String?,
)