package com.orka.myfinances.data.api.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class OrderItemApiModel(
    val id: Int,
    val order: Int,
    val product: OrderProductApiModel,
    val amount: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)