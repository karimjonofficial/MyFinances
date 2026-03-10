package com.orka.myfinances.application.viewmodels.warehouse

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenInteractor
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val category: Category,
    private val productTitleApi: ProductTitleApi,
    private val stockApi: StockApi,
    private val basketRepository: BasketRepository,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    productTitleEvents: Flow<ProductTitleEvent>,
    stockEvents: Flow<StockEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), WarehouseScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        productTitleEvents.onEach {
            if (it.categoryId == category.id) initialize()
        }.launchIn(viewModelScope)

        stockEvents.onEach {
            if (it.category.id == category.id) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val titlesModels = productTitleApi.getByCategory(category.id.value)
                val stockItemModels = stockApi.getByCategory(category.id.value)

                if (titlesModels != null && stockItemModels != null) {
                    val titles = titlesModels.map { it.map(category) }
                    val stockItems = stockItemModels.map { it.toUiModel(formatPrice, formatDecimal) }

                    setState(
                        State.Success(
                            WarehouseScreenModel(
                                category = category,
                                productTitles = titles.map { it.toUiModel() },
                                stockItems = stockItems
                            )
                        ))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun addToBasket(id: Id) {
        launch {
            logger.log("WarehouseScreenViewModel", "Add to basket id: $id")
            basketRepository.add(id.value, 1)
        }
    }

    override fun addProduct(category: Category) {
        launch {
            navigator.navigateToAddProduct(category.id)
        }
    }

    override fun receive(category: Category) {
        launch {
            navigator.navigateToAddStockItem(category.id)
        }
    }

    override fun selectProduct(id: Id) {
        launch {
            navigator.navigateToProductTitle(id)
        }
    }
}