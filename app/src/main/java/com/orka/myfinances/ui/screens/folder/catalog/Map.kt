package com.orka.myfinances.ui.screens.folder.catalog

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.fixtures.resources.models.folder.folders
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import com.orka.myfinances.ui.screens.folder.toButtonModel

fun Folder.toUiModel(): FolderUiModel {
    return FolderUiModel(
        model = toButtonModel(),
        id = id,
        isCatalog = this is Catalog
    )
}

fun Catalog.map(): CatalogScreenModel {
    return CatalogScreenModel(
        name = name,
        folders = folders.map { it.toUiModel() }
    )
}