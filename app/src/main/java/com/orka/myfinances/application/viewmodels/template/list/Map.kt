package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.ui.screens.templates.list.TemplateCardModel
import com.orka.myfinances.ui.screens.templates.list.TemplateUiModel

fun TemplateDto.toModel(formatDecimal: FormatDecimal): TemplateCardModel {
    return TemplateCardModel(
        title = name,
        size = formatDecimal.formatDecimal(fields.size.toDouble())
    )
}

fun TemplateDto.toUiModel(formatDecimal: FormatDecimal): TemplateUiModel {
    return TemplateUiModel(
        model = toModel(formatDecimal),
        id = Id(id)
    )
}