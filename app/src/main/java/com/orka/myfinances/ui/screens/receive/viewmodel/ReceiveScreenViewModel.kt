package com.orka.myfinances.ui.screens.receive.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
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

class ReceiveScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val loading: UiText,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            try {
                val response = client.get("receives/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val receive = response.body<ReceiveApiModel>().toUiModel(formatPrice, formatDateTime, formatDecimal)
                    setState(State.Success(receive))
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
}
