package com.orka.myfinances.data.api.product

import com.orka.myfinances.data.api.title.ProductTitleApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ProductApiModel(
    val id: Int,
    @SerialName("product_title") val title: ProductTitleApiModel,
    val price: Long,
    @SerialName("sale_price") val salePrice: Long,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("modified_at") val modifiedAt: Instant
)
