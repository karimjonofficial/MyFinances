package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreenInteractor
import com.orka.myfinances.ui.screens.templates.list.TemplatesScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val templateApi: TemplateApi,
    private val events: Flow<TemplateEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
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
                    updateState { TemplatesScreenState.Success(templates.map { it.toUiModel() }) }
                } else {
                    updateState { TemplatesScreenState.Error }
                }
            } catch (_: Exception) {
                updateState { TemplatesScreenState.Error }
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
}