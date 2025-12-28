package com.orka.myfinances.data.models.order

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.User
import kotlin.time.Instant

data class Order(
    val id: Id,
    val user: User,
    val client: Client,
    val items: List<OrderItem>,
    val price: Int,
    val dateTime: Instant,
    val endDateTime: Instant,
    val completed: Boolean,
    val description: String? = null
)