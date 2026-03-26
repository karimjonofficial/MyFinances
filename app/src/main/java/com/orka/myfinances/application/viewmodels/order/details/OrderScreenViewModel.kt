package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.details.OrderScreenInteractor
import com.orka.myfinances.ui.screens.order.details.OrderScreenModel
import kotlinx.coroutines.flow.asStateFlow

class OrderScreenViewModel(
    private val id: Id,
    private val orderApi: OrderApi,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    loading: UiText,
    private val failure: UiText,
    logger: Logger
) : SingleStateViewModel<State<OrderScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), OrderScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            val order = orderApi.getById(id.value)
            if (order != null) {
                setState(State.Success(order.toScreenModel(
                    formatPrice = formatPrice,
                    formatDateTime = formatDateTime,
                    formatDecimal = formatDecimal
                )))
            } else {
                setState(State.Failure(failure))
            }
        }
    }

    override fun navigateToClient(clientId: Id) {
        launch {
            navigator.navigateToClient(clientId)
        }
    }
}