package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val repository: TemplateRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val templates = repository.get()
        if (templates == null) updateState { TemplatesScreenState.Error }
        else updateState { TemplatesScreenState.Success(templates) }
    }
}