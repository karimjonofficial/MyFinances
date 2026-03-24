package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import kotlinx.coroutines.flow.StateFlow

interface StockContentInteractor : ChunkViewModel<StockItemUiModel> {
    val title: StateFlow<String?>
    fun addToBasket(id: Id)
    fun addProduct()
    fun receive()
}
