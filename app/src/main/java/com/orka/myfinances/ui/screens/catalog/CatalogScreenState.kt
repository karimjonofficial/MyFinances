package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.data.models.folder.Folder

sealed interface CatalogScreenState {
    object Loading : CatalogScreenState
    data class Success(val folders: List<Folder>) : CatalogScreenState
    data object Failure : CatalogScreenState
}