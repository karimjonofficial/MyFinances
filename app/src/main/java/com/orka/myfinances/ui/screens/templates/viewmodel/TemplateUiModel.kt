package com.orka.myfinances.ui.screens.templates.viewmodel

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.screens.templates.components.TemplateCardModel

data class TemplateUiModel(
    val model: TemplateCardModel,
    val template: Template
)