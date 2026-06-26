package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenInteractor
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    id: Id,
    private val repository: TemplateRepository,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<TemplateDto, TemplateScreenModel>(
    id = id,
    get = { repository.getById(it) },
    map = { it.toScreenModel() },
    loading = loading,
    failure = failure,
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