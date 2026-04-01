package com.orka.myfinances.data.api.order.models.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderProductTitleApiModel(
    val name: String
)