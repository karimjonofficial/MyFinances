package com.orka.myfinances.data.api.receive.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductMinimalApiModel(
    val id: Int,
    @SerialName("product_title") val title: ProductTitleMinimalApiModel
)