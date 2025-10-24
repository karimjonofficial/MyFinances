package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.core.DualStateViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.resources.models.id1
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class WarehouseScreenViewModel(
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
    logger: Logger,
    context: CoroutineContext
) : DualStateViewModel<WarehouseScreenProductsState, WarehouseScreenWarehouseState>(
    initialState1 = WarehouseScreenProductsState.Loading,
    initialState2 = WarehouseScreenWarehouseState.Loading,
    logger = logger,
    defaultCoroutineContext = context
) {
    val productsState = state1.asStateFlow()
    val warehouseState = state2.asStateFlow()

    fun initialize() = launch {
        val products = productRepository.get()
        val warehouse = warehouseRepository.get(id1)//TODO implement
        if (products != null)
            setState1(WarehouseScreenProductsState.Success(products))
        else setState1(WarehouseScreenProductsState.Failure)
        if(warehouse != null)
            setState2(WarehouseScreenWarehouseState.Success(warehouse))
        else setState2(WarehouseScreenWarehouseState.Failure)
    }
}