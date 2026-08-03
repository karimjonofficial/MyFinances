package com.orka.myfinances.ui.models.card

import kotlin.time.Instant

data class NotificationCardModel(
    val title: String,
    val message: String,
    val read: Boolean,
    val dateTime: Instant
)