package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.template.Template

sealed interface FolderType {
    data object Catalog : FolderType
    data class ProductFolder(val template: Template) : FolderType
}