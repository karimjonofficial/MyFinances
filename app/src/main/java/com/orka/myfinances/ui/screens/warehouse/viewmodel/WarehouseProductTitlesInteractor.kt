package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import kotlinx.coroutines.flow.StateFlow

interface WarehouseProductTitlesInteractor : ChunkViewModel<ProductTitleUiModel> {
    val title: StateFlow<String?>
    fun addProduct()
    fun receive()
    fun selectProduct(id: Id)
}
