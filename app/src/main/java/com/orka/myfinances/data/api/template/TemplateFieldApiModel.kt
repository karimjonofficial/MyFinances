package com.orka.myfinances.data.api.template

import kotlinx.serialization.Serializable

@Serializable
data class TemplateFieldApiModel(
    val id: Int,
    val name: String,
    val type: String
)