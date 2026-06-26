package com.orka.myfinances.data.dtos.notification

import kotlin.time.Instant

data class NotificationDto(
    val id: Int,
    val title: String,
    val message: String,
    val read: Boolean,
    val dateTime: Instant,
)
