package com.orka.myfinances.application.viewmodels.folder.catalog

import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.api.folder.CatalogApiModel
import com.orka.myfinances.data.api.folder.FolderApiModel
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenModel

fun CatalogApiModel.toScreenModel(folders: List<FolderApiModel>): CatalogScreenModel {
    return CatalogScreenModel(
        name = name,
        folders = folders.map { it.toUiModel() }
    )
}