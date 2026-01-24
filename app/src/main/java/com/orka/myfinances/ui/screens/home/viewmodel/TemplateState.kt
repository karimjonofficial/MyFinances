package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.data.models.template.Template

interface TemplateState {
    object Initial : TemplateState
    object Error : TemplateState
    data class Success(val templates: List<Template>) : TemplateState
}