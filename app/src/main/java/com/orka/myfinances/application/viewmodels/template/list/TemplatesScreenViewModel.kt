package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFul
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreenInteractor
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val templateApi: TemplateApi,
    private val events: Flow<TemplateEvent>,
    private val navigator: Navigator,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFul<State<TemplatesScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), TemplatesScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        launch {
            events.collect {
                initialize()
            }
        }
    }

    override fun initialize() {
        launch {
            try {
                val templates = templateApi.getAll()?.map { it.map() }
                if (templates != null) {
                    setState(
                        State.Success(
                            value = TemplatesScreenModel(templates.map { it.toUiModel() })
                        )
                    )
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun addTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }

    override fun select(template: Template) {
        launch {
            navigator.navigateToTemplate(template.id)
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val templates = templateApi.getAll()?.map { it.map() }
                if (templates != null) {
                    setState(
                        State.Success(
                            value = TemplatesScreenModel(templates.map { it.toUiModel() })
                        )
                    )
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}