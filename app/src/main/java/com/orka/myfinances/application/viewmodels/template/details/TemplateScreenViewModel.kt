package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenInteractor
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    id: Id,
    private val templateApi: TemplateApi,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<TemplateApiModel, TemplateScreenModel>(
    id = id,
    get = { templateApi.getById(it) },
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