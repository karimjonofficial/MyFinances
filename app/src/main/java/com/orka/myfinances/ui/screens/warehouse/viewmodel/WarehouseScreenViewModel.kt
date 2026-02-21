package com.orka.myfinances.ui.screens.warehouse.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val category: Category,
    private val getProductTitles: GetByParameter<ProductTitle, Category>,
    private val getStockItems: GetByParameter<StockItem, Category>,
    private val basketRepository: BasketRepository,
    private val priceFormatter: FormatPrice,
    private val decimalFormatter: FormatDecimal,
    productTitleEvents: Flow<ProductTitleEvent>,
    stockEvents: Flow<StockEvent>,
    private val navigator: Navigator,
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
            if (it.categoryId == category.id) initialize()
        }.launchIn(viewModelScope)

        stockEvents.onEach {
            if (it.category == category) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            val productTitles = getProductTitles.get(category)
            val stockItems = getStockItems.get(category)
            if (productTitles != null)
                setState1(ProductsState.Success(productTitles.map { it.toUiModel() }))
            else setState1(ProductsState.Failure)
            if (stockItems != null)
                setState2(WarehouseState.Success(category, stockItems.map { it.toUiModel(priceFormatter, decimalFormatter) }))
            else setState2(WarehouseState.Failure)
        }
    }

    fun addToBasket(stockItem: StockItem) {
        launch {
            basketRepository.add(stockItem.product.id, 1)
        }
    }

    fun addProduct(category: Category) {
        launch {
            navigator.navigateToAddProduct(category)
        }
    }

    fun receive(category: Category) {
        launch {
            navigator.navigateToAddStockItem(category)
        }
    }

    fun selectProduct(productTitle: ProductTitle) {
        launch {
            navigator.navigateToProductTitle(productTitle)
        }
    }
}
