package com.orka.myfinances.data.models

import com.orka.myfinances.data.models.folder.ProductFolder

data class Product(
    val id: Id,
    val name: String,
    val price: Double,
    val salePrice: Double,
    val folder: ProductFolder
)