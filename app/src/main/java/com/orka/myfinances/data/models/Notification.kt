package com.orka.myfinances.data.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Notification(
    val id: Id,
    val title: String,
    val message: String,
    val read: Boolean,
    val dateTime: Instant
)