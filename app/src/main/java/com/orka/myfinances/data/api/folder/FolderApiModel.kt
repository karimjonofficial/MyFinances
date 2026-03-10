package com.orka.myfinances.data.api.folder

import kotlinx.serialization.Serializable

@Serializable(with = FolderModelSerializer::class)
sealed interface FolderApiModel {
    val id: Int
    val name: String
}