package com.orka.myfinances.data.models

import com.orka.myfinances.data.models.product.Product
import kotlin.time.Instant

data class StockItem(
    val id: Id,
    val product: Product,
    val amount: Int,
    val office: Office,
    val dateTime: Instant,
    val comment: String? = null
)