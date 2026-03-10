package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.details.ClientInteractor
import kotlinx.coroutines.flow.asStateFlow

class ClientScreenViewModel(
    private val id: Id,
    private val clientApi: ClientApi,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), ClientInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val clientModel = clientApi.getById(id.value)
            if (clientModel != null) {
                setState(State.Success(clientModel))
            } else {
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
