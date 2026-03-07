package com.orka.myfinances.data.api.receive

import kotlinx.serialization.Serializable

@Serializable
data class ReceiveItemApiModel(
    val id: Int,
    val product: ProductMinimalApiModel,
    val amount: Long
)