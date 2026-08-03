package com.orka.myfinances.ui.screens.templates.list

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.models.card.TemplateCardModel
import com.orka.myfinances.ui.models.ui.TemplateUiModel

fun Template.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = fields?.size ?: 0
    )
}

fun Template.toUiModel(): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(),
        id = id
    )
}
