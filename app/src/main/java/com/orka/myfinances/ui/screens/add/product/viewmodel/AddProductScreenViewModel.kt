package com.orka.myfinances.ui.screens.add.product.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class AddProductScreenViewModel(
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository,
    logger: Logger,
    context: CoroutineContext = Dispatchers.Default
) : ViewModel<AddProductScreenState>(
    initialState = AddProductScreenState.Loading,
    logger = logger,
    defaultCoroutineContext = context
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val warehouses = warehouseRepository.get()
        if(warehouses != null) updateState { AddProductScreenState.Success(warehouses) }
        else updateState { AddProductScreenState.Failure }
    }

    fun addProduct(request: AddProductRequest) = launch {
        productRepository.add(request)
    }
}