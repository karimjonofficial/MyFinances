package com.orka.myfinances.application.viewmodels.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.api.folder.models.response.CatalogApiModel
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.components.FolderButtonModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

fun FolderApiModel.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes =  when (this) {
        is CatalogApiModel -> R.drawable.folder_filled
        is CategoryApiModel -> R.drawable.lists_filled
    }
)

fun FolderApiModel.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = Id(id),
        isCatalog = this is CatalogApiModel
    )
}