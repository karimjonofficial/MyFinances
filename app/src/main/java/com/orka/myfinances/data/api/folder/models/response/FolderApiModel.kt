package com.orka.myfinances.data.api.folder.models.response

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FolderApiModel(
    val id: Int,
    val name: String,
    @SerialName("is_catalog")
    val isCatalog: Boolean,
    val template: TemplateApiModel? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("modified_at")
    val modifiedAt: String? = null,
    @SerialName("created_by")
    val createdBy: Int? = null,
    @SerialName("modified_by")
    val modifiedBy: Int? = null,
    val branch: Int? = null,
    val parent: Int? = null
)
