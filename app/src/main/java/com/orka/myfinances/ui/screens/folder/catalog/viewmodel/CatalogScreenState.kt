package com.orka.myfinances.ui.screens.folder.catalog.viewmodel

import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

sealed interface CatalogScreenState {
    object Loading : CatalogScreenState
    data class Success(val folders: List<FolderUiModel>) : CatalogScreenState
    data object Failure : CatalogScreenState
}