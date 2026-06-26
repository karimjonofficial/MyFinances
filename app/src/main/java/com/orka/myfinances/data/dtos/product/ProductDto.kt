package com.orka.myfinances.data.dtos.product

import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import kotlin.time.Instant

data class ProductDto(
    val id: Int,
    val title: ProductTitleDto,
    val price: Long,
    val salePrice: Long,
    val exposedPrice: Long,
    val createdAt: Instant,
    val modifiedAt: Instant
)
