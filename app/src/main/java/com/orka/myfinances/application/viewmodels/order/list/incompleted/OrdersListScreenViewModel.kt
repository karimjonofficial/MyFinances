package com.orka.myfinances.application.viewmodels.order.list.incompleted

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.repositories.order.GetOrdersChunk
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.MapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.list.OrderUiModel
import com.orka.myfinances.ui.screens.order.list.OrdersScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OrdersListScreenViewModel(
    private val getOrdersChunk: GetOrdersChunk,
    events: Flow<OrderEvent>,
    formatDecimal: FormatDecimal,
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatLocalDate: FormatLocalDate,
    private val navigator: Navigator,
    logger: Logger
) : MapChunkViewModel<OrderDto, OrderUiModel>(
    get = { size, page -> getOrdersChunk.getOrdersChunk(size, page, false, null) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map =
            chunk.results.groupBy { orders -> orders.endDateTime?.toLocalDateTime(timeZone)?.date }
                .mapKeys { entry ->
                    if (entry.key != null) formatLocalDate.formatLocalDate(entry.key!!) else "End date time is not provided"
                }
                .mapValues { entry ->
                    entry.value.map { order ->
                        order.toUiModel(formatDecimal, formatPrice, formatDate)
                    }
                }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), OrdersScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
        initialize()
    }

    override fun select(order: OrderUiModel) {
        launch { navigator.navigateToOrder(order.id) }
    }
}
