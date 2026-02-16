package com.orka.myfinances.ui.screens.templates.viewmodel

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.screens.templates.components.TemplateCardModel

fun Template.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = "${fields.size}"
    )
}

fun Template.toUiModel(): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(),
        template = this
    )
}