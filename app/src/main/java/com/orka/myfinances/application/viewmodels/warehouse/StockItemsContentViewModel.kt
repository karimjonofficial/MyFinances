package com.orka.myfinances.application.viewmodels.warehouse

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.stock.StockApi1
import com.orka.myfinances.data.api.stock.StockItemApiModel
import com.orka.myfinances.data.api.stock.getByCategory
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.ChunkMapState
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockItemUiModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.StockContentInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StockItemsContentViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val stockApi: StockApi1,
    private val basketRepository: BasketRepository,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    stockEvents: Flow<StockEvent>,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<StockItemApiModel, StockItemUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> stockApi.getByCategory(size, page, categoryId) },
    map = { chunk ->
        val content = chunk.results
            .sortedBy { it.product.title.name }
            .groupBy { it.product.title.name.stickyHeaderKey() }
            .mapValues { (_, stockItems) ->
                stockItems.map { it.toUiModel(formatPrice, formatDecimal) }
            }

        ChunkMapState(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = content
        )
    },
    logger = logger
), StockContentInteractor {
    override val uiState = state.asStateFlow()

    private val titleState = MutableStateFlow<String?>(null)
    override val title = titleState.asStateFlow()

    init {
        initialize()
        loadTitle()

        stockEvents.onEach {
            if (it.categoryId == categoryId) initialize()
        }.launchIn(viewModelScope)
    }

    override fun addToBasket(id: Id) {
        launch { basketRepository.add(id, 1) }
    }

    override fun addProduct() {
        launch { navigator.navigateToAddProduct(categoryId) }
    }

    override fun receive() {
        launch { navigator.navigateToAddReceive(categoryId) }
    }

    private fun loadTitle() {
        launch {
            titleState.value = folderApi.getById(categoryId.value)?.name
        }
    }
}
