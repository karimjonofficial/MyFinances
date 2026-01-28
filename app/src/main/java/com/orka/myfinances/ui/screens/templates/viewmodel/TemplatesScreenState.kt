package com.orka.myfinances.ui.screens.templates.viewmodel

import com.orka.myfinances.data.models.template.Template

sealed interface TemplatesScreenState {
    data object Loading : TemplatesScreenState
    data class Success(val templates: List<Template>) : TemplatesScreenState
    data object Error : TemplatesScreenState
}