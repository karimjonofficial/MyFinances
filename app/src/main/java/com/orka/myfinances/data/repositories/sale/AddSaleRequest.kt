package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.Item

data class AddSaleRequest(
    val clientId: Id,
    val items: List<Item>,
    val price: Int,
    val description: String? = null
)