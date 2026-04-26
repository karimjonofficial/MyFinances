package com.orka.myfinances.application.viewmodels.order.list.incompleted

import com.orka.myfinances.application.viewmodels.order.list.OrdersScreenViewModel
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow

class OrdersListScreenViewModel(
    orderApi: OrderApi,
    events: Flow<OrderEvent>,
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatLocalDate: FormatLocalDate,
    navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : OrdersScreenViewModel(
    orderApi = orderApi,
    completed = false,
    events = events,
    formatPrice = formatPrice,
    formatDate = formatDate,
    formatLocalDate = formatLocalDate,
    navigator = navigator,
    loading = loading,
    failure = failure,
    logger = logger
)