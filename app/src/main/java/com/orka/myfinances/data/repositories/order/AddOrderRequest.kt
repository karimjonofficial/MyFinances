package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.lib.data.models.Item

class AddOrderRequest(
    val client: Int,
    val items: List<Item>,
    val price: Int,
    val description: String? = null
)