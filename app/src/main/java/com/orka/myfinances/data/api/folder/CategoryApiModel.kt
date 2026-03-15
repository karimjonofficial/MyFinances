package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.api.template.TemplateApiModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiModel(
    override val id: Int,
    override val name: String,
    val template: TemplateApiModel
) : FolderApiModel