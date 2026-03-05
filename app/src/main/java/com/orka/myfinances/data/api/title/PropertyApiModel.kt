package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.repositories.template.TemplateFieldApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyApiModel(
    val id: Int,
    @SerialName("field") val field: TemplateFieldApiModel,
    val value: String
)