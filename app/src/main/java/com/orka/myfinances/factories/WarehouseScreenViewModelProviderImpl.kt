package com.orka.myfinances.factories

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.factories.viewmodel.WarehouseScreenViewModelProvider
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import kotlinx.coroutines.CoroutineScope

class WarehouseScreenViewModelProviderImpl(
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
    private val logger: Logger,
    private val coroutineScope: CoroutineScope
) : WarehouseScreenViewModelProvider {
    override fun warehouseViewModel(warehouse: Warehouse): WarehouseScreenViewModel {
        return WarehouseScreenViewModel(
            warehouse = warehouse,
            productRepository = productRepository,
            stockRepository = stockRepository,
            logger = logger,
            coroutineScope = coroutineScope
        )
    }
}