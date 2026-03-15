package com.orka.myfinances.data.api.receive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductTitleMinimalApiModel(
    val id: Int,
    @SerialName("category") val categoryId: Int,
    val name: String
)