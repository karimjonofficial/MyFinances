package com.orka.myfinances.application.viewmodels.home.folder

import com.orka.myfinances.R
import com.orka.myfinances.data.api.folder.CatalogApiModel
import com.orka.myfinances.data.api.folder.CategoryApiModel
import com.orka.myfinances.data.api.folder.FolderApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.screens.home.models.FolderButtonModel
import com.orka.myfinances.ui.screens.home.models.FolderUiModel

fun FolderApiModel.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes =  when (this) {
        is CatalogApiModel -> R.drawable.folder_filled
        is CategoryApiModel -> R.drawable.lists_filled
    }
)

fun Folder.toButtonModel(): FolderButtonModel = FolderButtonModel(
    name = name,
    iconRes = when (this) {
        is Catalog -> R.drawable.folder_filled
        is Category -> R.drawable.lists_filled
    }
)

fun FolderApiModel.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = Id(id),
        isCatalog = this is CatalogApiModel
    )
}

fun Folder.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = id,
        isCatalog = this is Catalog
    )
}
