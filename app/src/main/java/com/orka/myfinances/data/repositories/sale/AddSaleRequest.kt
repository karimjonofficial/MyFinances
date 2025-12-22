package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.repositories.Item

data class AddSaleRequest(
    val client: Int,
    val items: List<Item>,
    val price: Int,
    val description: String? = null
)