package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFulViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenInteractor
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    private val id: Id,
    private val templateApi: TemplateApi,
    private val navigator: Navigator,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFulViewModel<State<TemplateScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), TemplateScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            val template = templateApi.getById(id.value)
            if (template != null) {
                setState(State.Success(template.toScreenModel()))
            } else {
                setState(State.Failure(failure))
            }
        }
    }

    override fun refresh() {
        launch {
            setState(State.Loading(loading))
            val template = templateApi.getById(id.value)
            if (template != null) {
                setState(State.Success(template.toScreenModel()))
            } else {
                setState(State.Failure(failure))
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}