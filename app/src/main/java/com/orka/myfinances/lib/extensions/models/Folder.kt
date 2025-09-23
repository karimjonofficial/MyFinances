package com.orka.myfinances.lib.extensions.models

import androidx.compose.runtime.saveable.Saver
import com.orka.myfinances.data.repositories.folder.FolderType

val FolderTypeSaver = Saver<FolderType, Pair<String, Int?>>(
    save = {
        when (it) {
            is FolderType.Catalog -> "catalog" to null
            is FolderType.ProductFolder -> "product" to it.templateId
        }
    },
    restore = {
        when (it.first) {
            "catalog" -> FolderType.Catalog
            "product" -> FolderType.ProductFolder(it.second!!)
            else -> error("Unknown FolderType")
        }
    }
)