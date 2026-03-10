package com.orka.myfinances.data.api.folder

import kotlinx.serialization.Serializable

@Serializable
data class CatalogApiModel(
    override val id: Int,
    override val name: String
) : FolderApiModel