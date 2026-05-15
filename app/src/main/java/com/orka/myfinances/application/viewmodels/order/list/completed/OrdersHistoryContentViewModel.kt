package com.orka.myfinances.application.viewmodels.order.list.completed

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.getChunk
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.list.completed.HistoryOrderUiModel
import com.orka.myfinances.ui.screens.order.list.completed.OrdersHistoryInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OrdersHistoryContentViewModel(
    orderApi: OrderApi,
    events: Flow<OrderEvent>,
    formatPrice: FormatPrice,
    formatTime: FormatTime,
    formatLocalDate: FormatLocalDate,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<OrderApiModel, HistoryOrderUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> orderApi.getChunk(size, page, true) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map =
            chunk.results.groupBy { orders -> orders.createdAt.toLocalDateTime(timeZone).date }
                .mapKeys { entry -> formatLocalDate.formatLocalDate(entry.key) }
                .mapValues { entry ->
                    entry.value.map { order ->
                        order.toUiModel(formatPrice, formatTime)
                    }
                }

        ChunkMapState(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
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