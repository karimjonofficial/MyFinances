package com.orka.myfinances.data.repositories.template

import kotlinx.serialization.Serializable

@Serializable
data class TemplateFieldModel(
    val name: String,
    val typeId: Int
)