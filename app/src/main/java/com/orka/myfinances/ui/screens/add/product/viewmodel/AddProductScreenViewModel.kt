package com.orka.myfinances.ui.screens.add.product.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

class AddProductScreenViewModel(
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<AddProductScreenState>(
    initialState = AddProductScreenState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val warehouse = warehouseRepository.get()
        if(warehouse != null) updateState { AddProductScreenState.Success(warehouse) }
        else updateState { AddProductScreenState.Failure }
    }

    fun addProduct(request: AddProductRequest) = launch {
        productRepository.add(request)
    }
}