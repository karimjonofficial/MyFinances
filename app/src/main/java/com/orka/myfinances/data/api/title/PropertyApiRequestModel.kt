package com.orka.myfinances.data.api.title

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyApiRequestModel(
    @SerialName("field") val id: Int,
    val value: String
)