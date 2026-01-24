package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.TemplateRepositoryEvent
import com.orka.myfinances.lib.data.repositories.GetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val repository: GetRepository<Template>,
    private val events: Flow<TemplateRepositoryEvent>,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    init {
        launch {
            events.collect {
                initialize()
            }
        }
    }

    fun initialize() = launch {
        val templates = repository.get()
        if (templates == null) updateState { TemplatesScreenState.Error }
        else updateState { TemplatesScreenState.Success(templates) }
    }
}