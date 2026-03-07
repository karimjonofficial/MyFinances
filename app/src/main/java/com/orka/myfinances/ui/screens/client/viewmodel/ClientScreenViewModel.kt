package com.orka.myfinances.ui.screens.client.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class ClientScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), ClientInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val response = client.get("clients/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val clientModel = response.body<ClientApiModel>()
                    setState(State.Success(clientModel))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}
