package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.Item
import kotlin.time.Instant

class AddOrderRequest(
    val clientId: Id,
    val items: List<Item>,
    val price: Int,
    val endDateTime: Instant? = null,
    val description: String? = null
)