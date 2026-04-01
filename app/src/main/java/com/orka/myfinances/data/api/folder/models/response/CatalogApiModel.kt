package com.orka.myfinances.data.api.folder.models.response

import kotlinx.serialization.Serializable

@Serializable
data class CatalogApiModel(
    override val id: Int,
    override val name: String
) : FolderApiModel