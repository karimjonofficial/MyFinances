package com.orka.myfinances.ui.models.card

import kotlin.time.Instant

data class OrderCardModel(
    val title: String,
    val price: Int,
    val dateTime: Instant,
    val size: Int,
    val completed: Boolean,
    val expired: Boolean
)