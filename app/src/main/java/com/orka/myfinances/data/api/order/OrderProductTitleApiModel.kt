package com.orka.myfinances.data.api.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderProductTitleApiModel(
    val name: String
)