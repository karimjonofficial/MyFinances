package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.api.product.ProductApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class StockItemApiModel(
    val id: Int,
    val product: ProductApiModel,
    val amount: Int,
    @SerialName("date_time") val dateTime: Instant? = null,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)
