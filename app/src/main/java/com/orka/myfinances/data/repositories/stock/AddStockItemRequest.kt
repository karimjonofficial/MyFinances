package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.models.Id

data class AddStockItemRequest(
    val productTitleId: Id,
    val amount: Int,
    val price: Int,
    val salePrice: Int,
    val comment: String? = null
)