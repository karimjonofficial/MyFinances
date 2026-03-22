package com.orka.myfinances.data.api.receive.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ReceiveItemApiModel(
    val id: Int,
    val product: ProductMinimalApiModel,
    val amount: Long
)