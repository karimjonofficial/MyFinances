package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.details.OrderScreenInteractor
import com.orka.myfinances.ui.screens.order.details.OrderScreenModel
import kotlin.time.Instant
import kotlinx.coroutines.flow.asStateFlow

class OrderScreenViewModel(
    id: Id,
    private val repository: OrderRepository,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<OrderDto, OrderScreenModel>(
    id = id,
    get = { repository.getById(it) },
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
        tryTransition { oldState ->
            if (repository.complete(id)) {
                refresh()
                oldState
            } else oldState
        }
    }

    override fun setEndDate(endDateTime: Instant) {
        tryTransition { oldState ->
            if (repository.setEndDate(id, endDateTime)) {
                refresh()
                oldState
            } else oldState
        }
    }
}
