package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.data.api.order.complete
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.details.OrderScreenInteractor
import com.orka.myfinances.ui.screens.order.details.OrderScreenModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderScreenViewModel(
    private val id: Id,
    private val orderApi: OrderApi,
    private val flow: MutableSharedFlow<OrderEvent>,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<OrderApiModel, OrderScreenModel>(
    id = id,
    get = { orderApi.getById(it) },
    map = { it.toScreenModel(formatPrice, formatDateTime, formatDecimal) },
    loading = loading,
    failure = failure,
    logger = logger
), OrderScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun navigateToClient(clientId: Id) {
        launch {
            navigator.navigateToClient(clientId)
        }
    }

    override fun complete() {
        launch {
            if (orderApi.complete(id)) {
                flow.emit(OrderEvent)
                refresh()
            }
        }
    }
}