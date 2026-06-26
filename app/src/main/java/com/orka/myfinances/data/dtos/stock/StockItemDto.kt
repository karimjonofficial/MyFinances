package com.orka.myfinances.data.dtos.stock

import com.orka.myfinances.data.dtos.product.ProductDto
import kotlin.time.Instant

data class StockItemDto(
    val id: Int,
    val product: ProductDto,
    val amount: Int,
    val dateTime: Instant?,
    val createdAt: Instant,
    val modifiedAt: Instant
)
