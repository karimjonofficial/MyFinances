package com.orka.myfinances.data.api.template.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemplateApiModel(
    val id: Int,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("modified_at")
    val modifiedAt: String? = null,
    val name: String,
    val description: String?,
    @SerialName("created_by")
    val createdBy: Int? = null,
    @SerialName("modified_by")
    val modifiedBy: Int? = null,
    val branches: List<Int>? = null,
    val fields: List<TemplateApiModelField>?
)
