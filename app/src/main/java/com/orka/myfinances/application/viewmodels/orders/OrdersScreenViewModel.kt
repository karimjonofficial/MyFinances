package com.orka.myfinances.application.viewmodels.orders

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.OrderApiModel
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.orders.OrderUiModel
import com.orka.myfinances.ui.screens.orders.OrdersScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class OrdersScreenViewModel(
    private val orderApi: OrderApi,
    private val priceFormatter: FormatPrice,
    private val dateFormatter: FormatDate,
    private val timeFormatter: FormatTime,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapViewModel<OrderApiModel, OrderUiModel>(
    loading = loading,
    failure = failure,
    get = { orderApi.getAll() },
    map = { orders ->
        orders.groupBy { dateFormatter.formatDate(it.createdAt) }
            .mapValues { entry ->
                entry.value.map { it.toUiModel(priceFormatter, dateFormatter, timeFormatter) }
            }
    },
    logger = logger
), IMapViewModel<OrderUiModel>, OrdersScreenInteractor {
    override val uiState = state.asStateFlow()

    override fun select(order: OrderUiModel) {
        launch { navigator.navigateToOrder(order.id) }
    }
}
