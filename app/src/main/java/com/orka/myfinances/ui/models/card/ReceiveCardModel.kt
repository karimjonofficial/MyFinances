package com.orka.myfinances.ui.models.card

import kotlin.time.Instant

data class ReceiveCardModel(
    val title: String,
    val price: Int,
    val size: Int,
    val dateTime: Instant
)