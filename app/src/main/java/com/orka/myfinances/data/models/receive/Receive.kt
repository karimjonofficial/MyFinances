package com.orka.myfinances.data.models.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.User
import kotlin.time.Instant

data class Receive(
    val id: Id,
    val user: User,
    val office: Office,
    val items: List<ReceiveItem>,
    val price: Int,
    val dateTime: Instant,
    val description: String? = null
)