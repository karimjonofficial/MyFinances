package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.order.OrderApiModel
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class OrdersScreenViewModel(
    private val client: HttpClient,
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
    get = {
        try {
            val response = client.get("orders/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<OrderApiModel>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
    map = { orders ->
        orders.groupBy { dateFormatter.formatDate(it.createdAt) }
            .mapValues { entry ->
                entry.value.map { it.toUiModel(priceFormatter, dateFormatter, timeFormatter) }
            }
    },
    logger = logger
), IMapViewModel<OrderUiModel>, OrdersInteractor {
    override val uiState = state.asStateFlow()

    override fun select(order: OrderUiModel) {
        launch { navigator.navigateToOrder(order.id) }
    }
}
