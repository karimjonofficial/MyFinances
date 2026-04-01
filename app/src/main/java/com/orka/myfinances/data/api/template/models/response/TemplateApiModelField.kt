package com.orka.myfinances.data.api.template.models.response

import kotlinx.serialization.Serializable

@Serializable
data class TemplateApiModelField(
    val id: Int,
    val name: String,
    val type: String
)