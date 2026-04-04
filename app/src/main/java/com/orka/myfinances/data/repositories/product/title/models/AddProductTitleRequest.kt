package com.orka.myfinances.data.repositories.product.title.models

import com.orka.myfinances.data.models.Id

data class AddProductTitleRequest(
    val categoryId: Id,
    val name: String,
    val price: Int,
    val salePrice: Int,
    val properties: List<PropertyModel<*>>,
    val description: String? = null
)