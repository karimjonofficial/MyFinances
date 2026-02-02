package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id

data class AddProductRequest(
    val titleId: Id,
    val price: Int,
    val salePrice: Int,
    val description: String? = null
)