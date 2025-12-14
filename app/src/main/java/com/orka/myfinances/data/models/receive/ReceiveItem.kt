package com.orka.myfinances.data.models.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product

data class ReceiveItem(
    val id: Id,
    val product: Product,
    val amount: Int
)