package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import kotlin.time.Instant

data class ProductTitle(
    val id: Id,
    val name: String,
    val defaultPrice: Int,
    val defaultSalePrice: Int,
    val dateTime: Instant,
    val category: Category,
    val properties: List<Property>,
    val description: String? = null
)