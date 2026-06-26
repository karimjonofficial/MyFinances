package com.orka.myfinances.application.viewmodels.folder.catalog

import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenModel

fun CatalogDto.toScreenModel(folders: List<FolderDto>): CatalogScreenModel {
    return CatalogScreenModel(
        name = name,
        folders = folders.map { it.toUiModel() }
    )
}