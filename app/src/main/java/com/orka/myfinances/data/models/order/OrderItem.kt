package com.orka.myfinances.data.models.order

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product

data class OrderItem(
    val id: Id,
    val product: Product,
    val amount: Int
)