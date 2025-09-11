package com.orka.myfinances.data.repositories

import kotlinx.serialization.Serializable

@Serializable
sealed interface FolderType {
    @Serializable data object Catalog : FolderType
    @Serializable data class ProductFolder(val templateId: Int) : FolderType
}