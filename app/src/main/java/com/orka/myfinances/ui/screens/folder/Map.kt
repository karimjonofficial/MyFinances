package com.orka.myfinances.ui.screens.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.screens.folder.components.FolderButtonModel

fun Folder.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes = when (this) {
        is Catalog -> R.drawable.folder_filled
        is Category -> R.drawable.lists_filled
    }
)