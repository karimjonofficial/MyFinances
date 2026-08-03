package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenInteractor
import com.orka.myfinances.ui.models.screen.TemplateScreenModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    id: Id,
    getById: GetById<TemplateDto>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<TemplateDto, TemplateScreenModel>(
    id = id,
    get = getById,
    map = { it.toScreenModel() },
    logger = logger
), TemplateScreenInteractor {
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