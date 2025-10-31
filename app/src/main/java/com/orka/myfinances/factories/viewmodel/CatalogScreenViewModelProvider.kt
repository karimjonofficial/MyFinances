package com.orka.myfinances.factories.viewmodel

import com.orka.myfinances.data.models.folder.Catalog

interface CatalogScreenViewModelProvider {
    fun catalogViewModel(catalog: Catalog): Any
}