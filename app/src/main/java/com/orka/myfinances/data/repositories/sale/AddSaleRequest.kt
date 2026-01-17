package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.lib.data.repositories.models.Item

data class AddSaleRequest(
    val clientId: Int,
    val items: List<Item>,
    val price: Int,
    val description: String? = null
)