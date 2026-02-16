package com.orka.myfinances.ui.screens.home.viewmodel.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.screens.home.models.FolderButtonModel
import com.orka.myfinances.ui.screens.home.models.FolderUiModel

fun Folder.toModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes =  when (this) {
        is Catalog -> R.drawable.folder_filled
        is Category -> R.drawable.lists_filled
    }
)

fun Folder.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toModel(),
        folder = this
    )
}