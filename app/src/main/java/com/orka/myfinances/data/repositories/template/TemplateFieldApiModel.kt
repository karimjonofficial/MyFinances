package com.orka.myfinances.data.repositories.template

import kotlinx.serialization.Serializable

@Serializable
data class TemplateFieldApiModel(
    val id: Int,
    val name: String,
    val type: String
)