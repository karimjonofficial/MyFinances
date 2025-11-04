package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Warehouse

data class Product(
    val id: Id,
    val name: String,
    val price: Int,
    val salePrice: Int,
    val warehouse: Warehouse,
    val properties: List<Property>,
    val description: String = ""
)