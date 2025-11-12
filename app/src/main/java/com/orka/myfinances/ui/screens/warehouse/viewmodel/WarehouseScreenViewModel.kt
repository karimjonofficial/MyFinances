package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.core.DualStateViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val warehouse: Warehouse,
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
    private val add: (StockItem) -> Unit,
    logger: Logger,
    coroutineScope: CoroutineScope
) : DualStateViewModel<WarehouseScreenProductsState, WarehouseScreenWarehouseState>(
    initialState1 = WarehouseScreenProductsState.Loading,
    initialState2 = WarehouseScreenWarehouseState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val productsState = state1.asStateFlow()
    val warehouseState = state2.asStateFlow()

    init {
        productRepository.events().onEach { initialize() }.launchIn(coroutineScope)
    }

    fun initialize() = launch {
        val products = productRepository.get(warehouse.id)
        val stockItems = stockRepository.get(warehouse.id)
        if (products != null)
            setState1(WarehouseScreenProductsState.Success(products))
        else setState1(WarehouseScreenProductsState.Failure)
        if (stockItems != null)
            setState2(WarehouseScreenWarehouseState.Success(warehouse, stockItems))
        else setState2(WarehouseScreenWarehouseState.Failure)
    }

    fun addToBasket(stockItem: StockItem) {
        this.add(stockItem)
    }
}