package com.orka.myfinances.ui.screens.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.screens.folder.components.FolderButtonModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

fun Folder.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes = when (this) {
        is Catalog -> R.drawable.folder_filled
        is Category -> R.drawable.lists_filled
    }
)

fun Folder.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = id,
        isCatalog = this is Catalog
    )
}