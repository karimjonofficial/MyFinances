package com.orka.myfinances.ui.screens.templates.list

import com.orka.myfinances.data.models.template.Template

fun Template.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = "${fields.size}"
    )
}

fun Template.toUiModel(): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(),
        id = id
    )
}