package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.DebtApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class DebtScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val formatPrice: FormatPrice,
    private val formatDate: FormatDate,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val response = client.get("merchant/debts/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val debt = response.body<DebtApiModel>()
                    val model = debt.map(formatPrice, formatDate)
                    setState(State.Success(model))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    fun back() {
        launch {
            navigator.back()
        }
    }

    fun navigateToClient(id: Id) {
        launch {
            navigator.navigateToClient(id)
        }
    }
}