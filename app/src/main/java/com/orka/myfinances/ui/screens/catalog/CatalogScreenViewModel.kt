package com.orka.myfinances.ui.screens.catalog

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.data.repositories.GetByParameterRepository
import kotlinx.coroutines.flow.asStateFlow

class CatalogScreenViewModel(
    private val catalog: Catalog,
    private val repository: GetByParameterRepository<Folder, Catalog>,
    logger: Logger
) : ViewModel<CatalogScreenState>(
    initialState = CatalogScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val folders = repository.get(catalog)
        if(folders != null)
            setState(CatalogScreenState.Success(folders))
        else setState(CatalogScreenState.Failure)
    }
}