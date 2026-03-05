package com.orka.myfinances.data.repositories.product.title.models

import com.orka.myfinances.data.models.folder.Category

data class AddProductTitleRequest(
    val category: Category,
    val name: String,
    val price: Int,
    val salePrice: Int,
    val properties: List<PropertyModel<*>>,
    val description: String? = null
)