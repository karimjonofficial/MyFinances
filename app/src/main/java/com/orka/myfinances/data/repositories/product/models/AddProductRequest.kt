package com.orka.myfinances.data.repositories.product.models

import com.orka.myfinances.data.models.Id

data class AddProductRequest(
    val titleId: Id,
    val name: String,
    val price: Int,
    val salePrice: Int,
    val properties: List<PropertyModel<*>>,
    val description: String? = null
)