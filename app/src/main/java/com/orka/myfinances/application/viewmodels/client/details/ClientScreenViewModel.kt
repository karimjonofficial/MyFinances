package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.details.ClientInteractor
import com.orka.myfinances.ui.screens.client.details.ClientScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ClientScreenViewModel(
    id: Id,
    getById: GetById<ClientDto>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<ClientDto, ClientScreenModel>(
    id = id,
    get = getById,
    map = ClientDto::toScreenModel,
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
