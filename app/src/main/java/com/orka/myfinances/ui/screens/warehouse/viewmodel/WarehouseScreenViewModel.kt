package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.core.DualStateViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val warehouse: Warehouse,
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
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
        val warehouse = warehouseRepository.get(warehouse.id)
        if (products != null)
            setState1(WarehouseScreenProductsState.Success(products))
        else setState1(WarehouseScreenProductsState.Failure)
        if (warehouse != null)
            setState2(WarehouseScreenWarehouseState.Success(warehouse))
        else setState2(WarehouseScreenWarehouseState.Failure)
    }
}