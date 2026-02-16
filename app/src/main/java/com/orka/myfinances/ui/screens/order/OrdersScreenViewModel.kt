package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapperListViewModel
import com.orka.myfinances.lib.viewmodel.list.ListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class OrdersScreenViewModel(
    repository: Get<Order>,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<Order, OrderUiModel>(
    loading = loading,
    failure = failure,
    repository = repository,
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<OrderUiModel> {
    override val uiState = state.asStateFlow()

    fun select(order: Order) {
        launch { navigator.navigateToOrder(order) }
    }
}