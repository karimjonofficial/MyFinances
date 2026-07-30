package com.orka.myfinances.ui.screens.order.list.completed

import kotlin.time.Instant

data class HistoryOrderCardModel(
    val title: String,
    val size: String,
    val price: String,
    val dateTime: Instant
)