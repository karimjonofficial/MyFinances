package com.orka.myfinances.ui.screens.warehouse.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderModel
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.stock.StockItemApiModel
import com.orka.myfinances.data.api.title.ProductTitleApiModel
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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WarehouseScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
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
), WarehouseInteractor {
    val uiState = state.asStateFlow()

    init {
        productTitleEvents.onEach {
            if (it.categoryId == id) initialize()
        }.launchIn(viewModelScope)

        stockEvents.onEach {
            if (it.category.id == id) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val categoryResponse = client.get("categories/${id.value}/")
                if (categoryResponse.status == HttpStatusCode.OK) {
                    val category = categoryResponse.body<FolderModel>().map() as Category

                    val titlesResponse = client.get(
                        urlString = "product-titles/",
                        block = { parameter("category", id.value) }
                    )
                    
                    val stockResponse = client.get(
                        urlString = "stock-items",
                        block = { parameter("category", id.value) }
                    )

                    if (titlesResponse.status == HttpStatusCode.OK && stockResponse.status == HttpStatusCode.OK) {
                        val titlesModels = titlesResponse.body<List<ProductTitleApiModel>>()
                        val titles = titlesModels.map { it.map(category) }
                        
                        val stockItemModels = stockResponse.body<List<StockItemApiModel>>()
                        val stockItems = stockItemModels.map { it.toUiModel(formatPrice, formatDecimal) }

                        setState(State.Success(WarehouseScreenState(
                            category = category,
                            productTitles = titles.map { it.toUiModel() },
                            stockItems = stockItems
                        )))
                    } else {
                        setState(State.Failure(UiText.Res(R.string.failure)))
                    }
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
            basketRepository.add(id, 1)
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

data class WarehouseScreenState(
    val category: Category,
    val productTitles: List<ProductTitleUiModel>,
    val stockItems: List<StockItemUiModel>
)
