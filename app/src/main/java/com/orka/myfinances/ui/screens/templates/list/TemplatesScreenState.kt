package com.orka.myfinances.ui.screens.templates.list

sealed interface TemplatesScreenState {
    data object Loading : TemplatesScreenState
    data class Success(val templates: List<TemplateUiModel>) : TemplatesScreenState
    data object Error : TemplatesScreenState
}