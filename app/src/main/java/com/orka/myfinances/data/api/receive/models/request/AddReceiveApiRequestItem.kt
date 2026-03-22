package com.orka.myfinances.data.api.receive.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddReceiveApiRequestItem(
    @SerialName("product_title") val productTitleId: Int,
    val price: Int?,
    @SerialName("sale_price") val salePrice: Int?,
    val amount: Int,
    val description: String? = null
)