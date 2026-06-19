package com.orka.myfinances.application.viewmodels.stock

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.stock.getByCategory
import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.stock.StockContentInteractor
import com.orka.myfinances.ui.screens.stock.StockItemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StockItemsContentViewModel(
    private val categoryId: Id,
    private val stockApi: StockApi,
    private val basketRepository: BasketRepository,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    stockEvents: Flow<StockEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<StockItemApiModel, StockItemUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> stockApi.getByCategory(size, page, categoryId, query) },
    map = { chunk ->
        val basketItems = basketRepository.get()
        val content = chunk.results
            .sortedBy { it.product.title.name }
            .groupBy { it.product.title.name.stickyHeaderKey() }
            .mapValues { (_, stockItems) ->
                stockItems.map {
                    it.toUiModel(
                        formatPrice = formatPrice,
                        formatDecimal = formatDecimal,
                        basketAmount = basketItems.find { basketItem -> basketItem.id == Id(it.product.id) }?.amount
                    )
                }
            }

        ChunkUiModel(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = content
        )
    },
    logger = logger
), StockContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()

        stockEvents.onEach {
            if (it.categoryId == categoryId) refresh()
        }.launchIn(viewModelScope)

        basketRepository.events.onEach { //TODO it can get optimization for increase and decrease
            tryTransition { oldState ->
                if (oldState is State.Success) {
                    val basketItems = basketRepository.get()
                    val newContent = oldState.value.content.mapValues { (_, items) ->
                        items.map { item ->
                            val basketItem = basketItems.find { it.id == item.id }
                            val amountStr = if (basketItem != null && basketItem.amount > 0) {
                                logger.log("StockItemsContentViewModel", "BasketItem found: ${basketItem.id}")
                                formatDecimal.formatDecimal(basketItem.amount.toDouble())
                            } else {
                                logger.log("StockItemsContentViewModel", "BasketItem not found: ${item.id}")
                                null
                            }
                            item.copy(
                                model = item.model.copy(
                                    basketAmount = amountStr,
                                    increaseEnabled = if (basketItem != null) item.amount > basketItem.amount else false
                                )
                            )
                        }
                    }
                    State.Success(oldState.value.copy(content = newContent))
                } else oldState
            }
        }.launchIn(viewModelScope)
    }

    override fun addToBasket(id: Id) {
        launch {
            logger.log("StockItemsContentViewModel", "Add to basket")
            basketRepository.add(id, 1)
        }
    }

    override fun removeFromBasket(id: Id) {
        launch { basketRepository.remove(id, 1) }
    }

    override fun expose() {
        tryTransition { oldState ->
            if(oldState is State.Success) {
                oldState.toExposed()
            } else State.Failure(failure, oldState.value)//TODO change error message
        }
    }

    override fun unExpose() {
        tryTransition { oldState ->
            if(oldState is State.Success) {
                oldState.toHidden()
            } else State.Failure(failure, oldState.value)//TODO change error message
        }
    }
}
