package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.repositories.folder.FolderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

class CatalogScreenViewModel(
    private val catalog: Catalog,
    private val repository: FolderRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<CatalogScreenState>(
    initialState = CatalogScreenState.Loading,
    coroutineScope = coroutineScope,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val folders = repository.get(catalog.id)
        if(folders != null)
            setState(CatalogScreenState.Success(folders))
        else setState(CatalogScreenState.Failure)
    }
}