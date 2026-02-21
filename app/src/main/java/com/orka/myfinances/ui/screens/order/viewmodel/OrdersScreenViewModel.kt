package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class OrdersScreenViewModel(
    get: Get<Order>,
    private val priceFormatter: FormatPrice,
    private val dateFormatter: FormatDate,
    private val timeFormatter: FormatTime,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapViewModel<Order, OrderUiModel>(
    loading = loading,
    failure = failure,
    get = get,
    map = { orders ->
        orders.groupBy { dateFormatter.formatDate(it.dateTime) }
            .mapValues { entry ->
                entry.value.map { it.toUiModel(priceFormatter, dateFormatter, timeFormatter) }
            }
    },
    logger = logger
), IMapViewModel<OrderUiModel> {
    override val uiState = state.asStateFlow()

    fun select(order: Order) {
        launch { navigator.navigateToOrder(order) }
    }
}