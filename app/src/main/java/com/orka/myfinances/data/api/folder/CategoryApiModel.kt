package com.orka.myfinances.data.api.folder

import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiModel(
    override val id: Int,
    override val name: String,
    val template: Int
) : FolderApiModel