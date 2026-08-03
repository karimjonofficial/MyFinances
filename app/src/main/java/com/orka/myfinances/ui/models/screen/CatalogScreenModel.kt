package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.ui.models.ui.FolderUiModel

data class CatalogScreenModel(
    val name: String,
    val folders: List<FolderUiModel>
)