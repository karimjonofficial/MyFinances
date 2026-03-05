package com.orka.myfinances.data.api.folder

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    override val id: Int,
    override val name: String,
    val template: Int
) : FolderModel