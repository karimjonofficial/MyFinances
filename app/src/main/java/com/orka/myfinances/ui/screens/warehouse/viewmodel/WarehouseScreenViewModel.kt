package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.core.DualStateViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.stock.StockRepository
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
) : DualStateViewModel<ProductsState, WarehouseState>(
    initialState1 = ProductsState.Loading,
    initialState2 = WarehouseState.Loading,
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
            setState1(ProductsState.Success(products))
        else setState1(ProductsState.Failure)
        if (stockItems != null)
            setState2(WarehouseState.Success(warehouse, stockItems))
        else setState2(WarehouseState.Failure)
    }

    fun addToBasket(stockItem: StockItem) {
        this.add(stockItem)
    }
}