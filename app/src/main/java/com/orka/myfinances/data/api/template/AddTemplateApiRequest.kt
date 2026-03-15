package com.orka.myfinances.data.api.template

import kotlinx.serialization.Serializable

@Serializable
data class AddTemplateApiRequest(
    val name: String,
    val branch: Int,
    val fields: List<AddTemplateApiRequestField>
)