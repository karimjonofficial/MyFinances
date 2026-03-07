package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.api.receive.ProductMinimalApiModel
import kotlinx.serialization.Serializable

@Serializable
data class SaleItemApiModel(
    val id: Int,
    val product: ProductMinimalApiModel,
    val amount: Long
)