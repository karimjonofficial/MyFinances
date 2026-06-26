package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.details.ClientInteractor
import com.orka.myfinances.ui.screens.client.details.ClientScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ClientScreenViewModel(
    id: Id,
    private val repository: ClientRepository,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<ClientDto, ClientScreenModel>(
    id = id,
    get = { repository.getById(id) },
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
