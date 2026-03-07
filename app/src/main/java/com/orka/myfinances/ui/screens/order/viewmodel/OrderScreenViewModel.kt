package com.orka.myfinances.ui.screens.order.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

interface OrderInteractor {
    fun navigateToClient(clientId: Id)
}

class OrderScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val formatTime: FormatTime,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), OrderInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val response = client.get("orders/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val order = response.body<Order>()
                    setState(State.Success(order.map(formatDate, formatTime, formatPrice)))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun navigateToClient(clientId: Id) {
        launch {
            navigator.navigateToClient(clientId)
        }
    }
}
