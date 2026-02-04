package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id
import kotlin.time.Instant

data class Product(
    val id: Id,
    val title: ProductTitle,
    val price: Int,
    val salePrice: Int,
    val dateTime: Instant
)