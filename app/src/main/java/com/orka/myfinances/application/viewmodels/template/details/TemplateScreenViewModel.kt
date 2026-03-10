package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    private val id: Id,
    private val templateApi: TemplateApi,
    private val failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), TemplateScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val template = templateApi.getById(id.value)
            if (template != null) {
                setState(State.Success(template))
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