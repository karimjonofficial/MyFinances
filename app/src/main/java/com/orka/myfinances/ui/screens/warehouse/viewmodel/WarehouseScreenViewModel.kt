package com.orka.myfinances.ui.screens.warehouse.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.DualStateViewModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleRepositoryEvent
import com.orka.myfinances.data.repositories.stock.StockRepositoryEvent
import com.orka.myfinances.lib.data.repositories.GetByParameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val category: Category,
    private val productTitleRepository: GetByParameter<ProductTitle, Category>,
    private val stockRepository: GetByParameter<StockItem, Category>,
    private val basketRepository: BasketRepository,
    productTitleEvents: Flow<ProductTitleRepositoryEvent>,
    stockEvents: Flow<StockRepositoryEvent>,
    logger: Logger
) : DualStateViewModel<ProductsState, WarehouseState>(
    initialState1 = ProductsState.Loading,
    initialState2 = WarehouseState.Loading,
    logger = logger
) {
    val productTitlesState = state1.asStateFlow()
    val warehouseState = state2.asStateFlow()

    init {
        productTitleEvents.onEach {
            if (it is ProductTitleRepositoryEvent.Add && it.categoryId == category.id) {
                initialize()
            }
        }.launchIn(viewModelScope)

        stockEvents.onEach {
            if (it.category == category) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            val productTitles = productTitleRepository.get(category)
            val stockItems = stockRepository.get(category)
            if (productTitles != null)
                setState1(ProductsState.Success(productTitles))
            else setState1(ProductsState.Failure)
            if (stockItems != null)
                setState2(WarehouseState.Success(category, stockItems))
            else setState2(WarehouseState.Failure)
        }
    }

    fun addToBasket(stockItem: StockItem) {
        launch(Dispatchers.Default) {
            basketRepository.add(stockItem.product.id, 1)
        }
    }
}