package com.orka.myfinances.ui.screens.products.add.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

class AddProductScreenViewModel(
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<AddProductScreenState>(
    initialState = AddProductScreenState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val warehouse = stockRepository.get()
        if(warehouse != null) updateState { AddProductScreenState.Success(warehouse) }
        else updateState { AddProductScreenState.Failure }
    }

    fun addProduct(request: AddProductRequest) = launch {
        productRepository.add(request)
    }
}