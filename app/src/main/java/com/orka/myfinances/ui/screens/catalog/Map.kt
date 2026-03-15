package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.application.viewmodels.home.folder.toUiModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.fixtures.resources.models.folder.folders

fun Catalog.map(): CatalogScreenModel {
    return CatalogScreenModel(
        name = name,
        folders = folders.map { it.toUiModel() }
    )
}