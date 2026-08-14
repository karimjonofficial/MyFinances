package com.orka.myfinances.application.viewmodels.order.list.completed

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.repositories.order.GetOrdersChunk
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.HistoryOrderUiModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.list.completed.OrdersHistoryInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OrdersHistoryContentViewModel(
    private val getOrdersChunk: GetOrdersChunk,
    events: Flow<OrderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SearchableMapChunkViewModel<OrderDto, HistoryOrderUiModel>(
    get = { size, page -> getOrdersChunk.getOrdersChunk(size, page, true, null) },
    search = { size, page, query -> getOrdersChunk.getOrdersChunk(size, page, true, query) },
    map = { it.toUiModel() },
    groupBy = { it.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString() },
    logger = logger
), OrdersHistoryInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
        initialize()
    }

    override fun select(order: HistoryOrderUiModel) {
        launch { navigator.navigateToOrder(order.id) }
    }
}
