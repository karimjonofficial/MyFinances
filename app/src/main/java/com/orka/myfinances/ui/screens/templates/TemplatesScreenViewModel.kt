package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.data.repositories.TemplateRepositoryEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val repository: TemplateRepository,
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