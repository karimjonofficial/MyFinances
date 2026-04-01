package com.orka.myfinances.application.viewmodels.order.list

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.OrderApiModel
import com.orka.myfinances.lib.data.api.getChunk
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.list.OrderUiModel
import com.orka.myfinances.ui.screens.order.list.OrdersScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OrdersScreenViewModel(
    private val orderApi: OrderApi,
    private val formatPrice: FormatPrice,
    private val formatTime: FormatTime,
    private val formatLocalDate: FormatLocalDate,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<OrderApiModel, OrderUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> orderApi.getChunk(size, page) },
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
), OrdersScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun select(order: OrderUiModel) {
        launch { navigator.navigateToOrder(order.id) }
    }
}
