package com.orka.myfinances.data.models

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Debt(
    val id: Id,
    val user: User,
    val client: Client,
    val price: Int,
    val notified: Boolean,
    val dateTime: Instant,
    val endDateTime: Instant,
    val description: String? = null
)
