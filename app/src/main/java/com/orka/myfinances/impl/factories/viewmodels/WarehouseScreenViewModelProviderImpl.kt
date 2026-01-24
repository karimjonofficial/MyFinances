package com.orka.myfinances.impl.factories.viewmodels

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.ProductRepositoryEvent
import com.orka.myfinances.factories.viewmodel.WarehouseScreenViewModelProvider
import com.orka.myfinances.lib.data.repositories.GetByParameterRepository
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class WarehouseScreenViewModelProviderImpl(
    private val productRepository: GetByParameterRepository<Product, Category>,
    private val stockRepository: GetByParameterRepository<StockItem, Category>,
    private val addToBasket: (StockItem) -> Unit,
    private val events: Flow<ProductRepositoryEvent>,
    private val logger: Logger,
    private val coroutineScope: CoroutineScope
) : WarehouseScreenViewModelProvider {
    override fun warehouseViewModel(category: Category): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            category = category,
            productRepository = productRepository,
            stockRepository = stockRepository,
            add = addToBasket,
            logger = logger,
            events = events,
            coroutineScope = coroutineScope
        )
    }
}