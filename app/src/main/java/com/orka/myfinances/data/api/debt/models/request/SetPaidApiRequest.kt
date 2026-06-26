package com.orka.myfinances.data.api.debt.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetPaidApiRequest(
    @SerialName("is_completed") val completed: Boolean
)