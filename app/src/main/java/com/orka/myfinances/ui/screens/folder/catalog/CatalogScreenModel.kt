package com.orka.myfinances.ui.screens.folder.catalog

import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

data class CatalogScreenModel(
    val name: String,
    val folders: List<FolderUiModel>
)