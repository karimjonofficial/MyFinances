package com.orka.myfinances.ui.screens.templates.viewmodel

sealed interface TemplatesScreenState {
    data object Loading : TemplatesScreenState
    data class Success(val templates: List<TemplateUiModel>) : TemplatesScreenState
    data object Error : TemplatesScreenState
}