package com.orka.myfinances.ui.screens.templates.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val get: Get<Template>,
    private val events: Flow<TemplateEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        launch {
            events.collect {
                initialize()
            }
        }
    }

    override fun initialize() {
        launch {
            val templates = get.get()
            if (templates == null) updateState { TemplatesScreenState.Error }
            else updateState { TemplatesScreenState.Success(templates.map { it.toUiModel() }) }
        }
    }

    fun addTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }

    fun select(template: Template) {
        launch {
            navigator.navigateToTemplate(template)
        }
    }
}