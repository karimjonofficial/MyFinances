package com.orka.myfinances.application.viewmodels.receive.details

import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreenInteractor
import com.orka.myfinances.ui.models.screen.ReceiveScreenModel
import kotlinx.coroutines.flow.asStateFlow

class ReceiveScreenViewModel(
    id: Id,
    getById: GetById<ReceiveDto>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<ReceiveDto, ReceiveScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel() },
    logger = logger
), ReceiveScreenInteractor {
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