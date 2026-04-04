package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.details.ClientInteractor
import com.orka.myfinances.ui.screens.client.details.ClientScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ClientScreenViewModel(
    private val id: Id,
    private val clientApi: ClientApi,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<ClientApiModel, ClientScreenModel>(
    id = id,
    get = { clientApi.getById(id) },
    map = { it.toScreenModel() },
    loading = loading,
    failure = failure,
    logger = logger
), ClientInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}
