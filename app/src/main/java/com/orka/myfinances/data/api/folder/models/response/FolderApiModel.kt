package com.orka.myfinances.data.api.folder.models.response

import kotlinx.serialization.Serializable

@Serializable(with = FolderModelSerializer::class)
sealed interface FolderApiModel {
    val id: Int
    val name: String
}