package com.orka.myfinances.application.viewmodels.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.dtos.folder.CategoryDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.components.FolderButtonModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

fun FolderDto.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes =  when (this) {
        is CatalogDto -> R.drawable.folder_filled
        is CategoryDto -> R.drawable.lists_filled
    }
)

fun FolderDto.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = Id(id),
        isCatalog = this is CatalogDto
    )
}