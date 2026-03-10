package com.orka.myfinances.ui.screens.catalog.viewmodel

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.ui.screens.home.models.FolderUiModel

data class CatalogScreenModel(
    val catalog: Catalog,
    val folders: List<FolderUiModel>
)