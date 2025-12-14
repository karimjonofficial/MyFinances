package com.orka.myfinances.data.models.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Receive(
    val id: Id,
    val user: User,
    val items: List<ReceiveItem>,
    val price: Int,
    val dateTime: Instant,
    val description: String? = null
)