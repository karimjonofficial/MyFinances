package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.TemplateCardModel
import com.orka.myfinances.ui.models.ui.TemplateUiModel

fun TemplateDto.toModel(): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = fields?.size ?: 0
    )
}

fun TemplateDto.toUiModel(): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(),
        id = Id(id)
    )
}
