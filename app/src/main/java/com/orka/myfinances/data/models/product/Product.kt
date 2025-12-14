package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Warehouse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Product(
    val id: Id,
    val title: ProductTitle,
    val price: Int,
    val salePrice: Int,
    val dateTime: Instant,
    val warehouse: Warehouse,
    val properties: List<Property>,
    val description: String? = null
)