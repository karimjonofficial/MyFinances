package com.orka.myfinances.data.repositories.product.models

import com.orka.myfinances.data.repositories.product.models.PropertyModel

data class AddProductRequest(
    val name: String,
    val warehouseId: Int,
    val price: Int,
    val salePrice: Int,
    val properties: List<PropertyModel<*>>,
    val description: String
)