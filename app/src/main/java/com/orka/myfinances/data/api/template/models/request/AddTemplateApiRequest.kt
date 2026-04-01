package com.orka.myfinances.data.api.template.models.request

import kotlinx.serialization.Serializable

@Serializable
data class AddTemplateApiRequest(
    val name: String,
    val branch: Int,
    val fields: List<AddTemplateApiRequestField>
)