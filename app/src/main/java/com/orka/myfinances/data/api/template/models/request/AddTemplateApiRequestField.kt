package com.orka.myfinances.data.api.template.models.request

import kotlinx.serialization.Serializable

@Serializable
data class AddTemplateApiRequestField(
    val name: String,
    val type: String
)