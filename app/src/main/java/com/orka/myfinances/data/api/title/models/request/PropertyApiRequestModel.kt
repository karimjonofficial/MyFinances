package com.orka.myfinances.data.api.title.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyApiRequestModel(
    @SerialName("field") val id: Int,
    val value: String
)