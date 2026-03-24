package com.orka.myfinances.data.api.title.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ProductTitleApiModel(
    val id: Int,
    val category: Int,
    val name: String,
    val properties: List<PropertyApiModel>,
    @SerialName("default_price") val defaultPrice: Long,
    @SerialName("default_sale_price") val defaultSalePrice: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant,
    val description: String? = null
)