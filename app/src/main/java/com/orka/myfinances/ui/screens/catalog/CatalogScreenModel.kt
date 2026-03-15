package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.ui.screens.home.models.FolderUiModel

data class CatalogScreenModel(
    val name: String,
    val folders: List<FolderUiModel>
)