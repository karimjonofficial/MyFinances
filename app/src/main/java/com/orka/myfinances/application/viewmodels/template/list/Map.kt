package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.screens.templates.list.TemplateCardModel
import com.orka.myfinances.ui.screens.templates.list.TemplateUiModel

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