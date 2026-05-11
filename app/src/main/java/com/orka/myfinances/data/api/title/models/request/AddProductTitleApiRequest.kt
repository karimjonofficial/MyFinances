package com.orka.myfinances.data.api.title.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddProductTitleApiRequest(
    val category: Int,
    val name: String,
    val branch: Int,
    val properties: List<PropertyApiRequestModel>,
    @SerialName("default_price") val defaultPrice: Long,
    @SerialName("default_sale_price") val defaultSalePrice: Long,
    @SerialName("default_exposed_price") val defaultExposedPrice: Long,
    val description: String?
)