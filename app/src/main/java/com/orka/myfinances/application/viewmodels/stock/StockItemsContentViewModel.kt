package com.orka.myfinances.application.viewmodels.stock

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.stock.StockItemDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.stock.GetStockItemsByCategory
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.StockItemUiModel
import com.orka.myfinances.ui.screens.stock.StockContentInteractor
import com.orka.myfinances.ui.statuses.failure.ExecutedFromFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StockItemsContentViewModel(
    private val categoryId: Id,
    private val getByCategory: GetStockItemsByCategory,
    stockEvents: Flow<StockEvent>,
    private val basketRepository: BasketRepository,
    logger: Logger
) : SearchableMapChunkViewModel<StockItemDto, StockItemUiModel>(
    get = { size, page -> getByCategory.getByCategory(size, page, categoryId, null) },
    search = { size, page, q -> getByCategory.getByCategory(size, page, categoryId, q) },
    map = {
        val basketItems = basketRepository.get()
        it.toUiModel(
            basketAmount = basketItems.find { basketItem -> basketItem.id == Id(it.product.id) }?.amount
        )
    },
    groupBy = { it.product.title.name.stickyHeaderKey() },
    logger = logger
), StockContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()

        stockEvents.onEach { event: StockEvent ->
            if (event.categoryId == categoryId) refresh()
        }.launchIn(viewModelScope)

        basketRepository.events.onEach { //TODO it can get optimization for increase and decrease
            tryTransition { oldState ->
                if (oldState is State.Success) {
                    val basketItems = basketRepository.get()
                    val newContent = oldState.value.content.mapValues { (_, items) ->
                        items.map { item ->
                            val basketItem = basketItems.find { it.id == item.id }
                            val amount = if (basketItem != null && basketItem.amount > 0) {
                                logger.log("StockItemsContentViewModel", "BasketItem found: ${basketItem.id}")
                                basketItem.amount
                            } else {
                                logger.log("StockItemsContentViewModel", "BasketItem not found: ${item.id}")
                                null
                            }
                            item.copy(
                                model = item.model.copy(
                                    basketAmount = amount,
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
            } else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }

    override fun unExpose() {
        tryTransition { oldState ->
            if(oldState is State.Success) {
                oldState.toHidden()
            } else State.Failure(ExecutedFromFailure, oldState.value)
        }
    }
}
