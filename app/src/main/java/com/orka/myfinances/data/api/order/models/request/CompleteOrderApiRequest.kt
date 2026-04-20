package com.orka.myfinances.data.api.order.models.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteOrderApiRequest(
    val id: Int,
    val completed: Boolean
)
