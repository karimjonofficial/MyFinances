package com.orka.myfinances.data.models.product

import com.orka.myfinances.data.models.Id

data class ProductTitle(
    val id: Id,
    val name: String,
    val description: String? = null
)