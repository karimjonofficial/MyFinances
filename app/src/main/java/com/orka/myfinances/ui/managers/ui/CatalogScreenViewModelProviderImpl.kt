package com.orka.myfinances.ui.managers.ui

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.factories.viewmodel.CatalogScreenViewModelProvider
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import kotlinx.coroutines.CoroutineScope

class CatalogScreenViewModelProviderImpl(
    private val repository: FolderRepository,
    private val logger: Logger,
    private val coroutineScope: CoroutineScope
) : CatalogScreenViewModelProvider {
    override fun catalogViewModel(catalog: Catalog): Any {
        return CatalogScreenViewModel(
            catalog = catalog,
            repository = repository,
            logger = logger,
            coroutineScope = coroutineScope
        )
    }
}