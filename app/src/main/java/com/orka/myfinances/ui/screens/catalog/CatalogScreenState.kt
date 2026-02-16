package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.ui.screens.home.models.FolderUiModel

sealed interface CatalogScreenState {
    object Loading : CatalogScreenState
    data class Success(val folders: List<FolderUiModel>) : CatalogScreenState
    data object Failure : CatalogScreenState
}