package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class TemplatesScreenViewModel(
    private val repository: TemplateRepository,
    logger: Logger,
    context: CoroutineContext = Dispatchers.Default
) : ViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
    logger = logger,
    defaultCoroutineContext = context
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val templates = repository.get()
        if (templates == null) updateState { TemplatesScreenState.Error }
        else updateState { TemplatesScreenState.Success(templates) }
    }
}