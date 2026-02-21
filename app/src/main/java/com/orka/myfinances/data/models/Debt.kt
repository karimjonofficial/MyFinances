package com.orka.myfinances.data.models

import kotlin.time.Instant

data class Debt(
    val id: Id,
    val user: User,
    val client: Client,
    val price: Int,
    val notified: Boolean,
    val dateTime: Instant,
    val endDateTime: Instant? = null,
    val description: String? = null
)