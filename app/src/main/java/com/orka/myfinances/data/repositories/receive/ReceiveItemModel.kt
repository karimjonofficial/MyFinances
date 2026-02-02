package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.models.Id

data class ReceiveItemModel(
    val productTitleId: Id,
    val price: Int,
    val salePrice: Int,
    val amount: Int,
    val description: String? = null
)