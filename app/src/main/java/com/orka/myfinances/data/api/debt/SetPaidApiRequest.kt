package com.orka.myfinances.data.api.debt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetPaidApiRequest(
    @SerialName("is_completed") val completed: Boolean
)