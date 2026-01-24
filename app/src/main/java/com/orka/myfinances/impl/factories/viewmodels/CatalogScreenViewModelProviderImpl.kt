package com.orka.myfinances.impl.factories.viewmodels

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.factories.viewmodel.CatalogScreenViewModelProvider
import com.orka.myfinances.lib.data.repositories.GetByParameterRepository
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import kotlinx.coroutines.CoroutineScope

class CatalogScreenViewModelProviderImpl(
    private val logger: Logger,
    private val repository: GetByParameterRepository<Folder, Catalog>,
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