package com.orka.myfinances.data.api.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class OrderProductApiModel(
    val id: Int,
    @SerialName("product_title") val title: OrderProductTitleApiModel,
    val branch: Int,
    val price: Long,
    @SerialName("sale_price") val salePrice: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)