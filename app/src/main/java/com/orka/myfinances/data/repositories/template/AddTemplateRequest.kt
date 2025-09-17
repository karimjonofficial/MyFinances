package com.orka.myfinances.data.repositories.template

import kotlinx.serialization.Serializable

@Serializable
data class AddTemplateRequest(
    val name: String,
    val fields: List<TemplateFieldModel>
)