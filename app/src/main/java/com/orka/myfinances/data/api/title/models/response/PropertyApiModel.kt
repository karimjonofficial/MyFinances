package com.orka.myfinances.data.api.title.models.response

import com.orka.myfinances.data.api.template.TemplateApiModelField
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyApiModel(
    val id: Int,
    @SerialName("field") val field: TemplateApiModelField,
    val value: String
)