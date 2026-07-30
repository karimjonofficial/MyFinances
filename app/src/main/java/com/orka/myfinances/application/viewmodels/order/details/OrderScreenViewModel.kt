package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.CompleteOrder
import com.orka.myfinances.data.repositories.order.SetEndDate
import com.orka.myfinances.format.FormatDateTime
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.details.OrderScreenInteractor
import com.orka.myfinances.ui.screens.order.details.OrderScreenModel
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Instant

class OrderScreenViewModel(
    id: Id,
    getById: GetById<OrderDto>,
    private val completeOrder: CompleteOrder,
    private val setEndDate: SetEndDate,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<OrderDto, OrderScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel(formatPrice, formatDateTime, formatDecimal) },
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
            if (completeOrder.complete(id)) {
                refresh()
                oldState
            } else oldState
        }
    }

    override fun setEndDate(endDateTime: Instant) {
        tryTransition { oldState ->
            if (setEndDate.setEndDate(id, endDateTime)) {
                refresh()
                oldState
            } else oldState
        }
    }
}
