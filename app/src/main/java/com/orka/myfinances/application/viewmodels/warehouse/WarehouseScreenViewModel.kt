package com.orka.myfinances.application.viewmodels.warehouse

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.extensions.isInitial
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenInteractor
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val stockApi: StockApi,
    private val basketRepository: BasketRepository,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    productTitleEvents: Flow<ProductTitleEvent>,
    stockEvents: Flow<StockEvent>,
    private val navigator: Navigator,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : SingleStateViewModel<State<WarehouseScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), WarehouseScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        productTitleEvents.onEach {
            if (it.categoryId == categoryId) initialize()
        }.launchIn(viewModelScope)

        stockEvents.onEach {
            if (it.categoryId == categoryId) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        logger.log("WarehouseScreenViewModel", "Initialize called")
        launch {
            try {
                if (!state.value.isInitial()) setState(State.Loading(loading))
                val category = folderApi.getById(categoryId.value)
                val titlesModels = productTitleApi.getByCategory(categoryId.value)
                val stockItemModels = stockApi.getByCategory(categoryId)

                if (titlesModels != null && stockItemModels != null && category != null) {
                    val titles = titlesModels.map { it.toUiModel() }
                    val stockItems =
                        stockItemModels.map { it.toUiModel(formatPrice, formatDecimal) }

                    setState(
                        State.Success(
                            WarehouseScreenModel(
                                title = category.name,
                                productTitles = titles,
                                stockItems = stockItems
                            )
                        )
                    )
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun addToBasket(id: Id) {
        launch {
            basketRepository.add(id, 1)
        }
    }

    override fun addProduct() {
        launch {
            navigator.navigateToAddProduct(categoryId)
        }
    }

    override fun receive() {
        launch {
            navigator.navigateToAddReceive(categoryId)
        }
    }

    override fun selectProduct(id: Id) {
        launch {
            navigator.navigateToProductTitle(id)
        }
    }
}