package com.orka.myfinances.data.api.template

import kotlinx.serialization.Serializable

@Serializable
data class TemplateApiModel(
    val id: Int,
    val name: String,
    val fields: List<TemplateFieldApiModel>,
    val description: String?
)