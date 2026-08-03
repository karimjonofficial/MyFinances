package com.orka.myfinances.ui.models.card

import kotlin.time.Instant

data class HistoryOrderCardModel(
    val title: String,
    val size: Int,
    val price: Int,
    val dateTime: Instant
)