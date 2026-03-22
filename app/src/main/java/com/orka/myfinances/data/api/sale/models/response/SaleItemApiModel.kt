package com.orka.myfinances.data.api.sale.models.response

import com.orka.myfinances.data.api.receive.models.response.ProductMinimalApiModel
import kotlinx.serialization.Serializable

@Serializable
data class SaleItemApiModel(
    val id: Int,
    val product: ProductMinimalApiModel,
    val amount: Long
)