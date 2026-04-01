package com.orka.myfinances.data.api.folder.models.response

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiModel(
    override val id: Int,
    override val name: String,
    val template: TemplateApiModel
) : FolderApiModel