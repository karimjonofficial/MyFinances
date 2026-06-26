package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModelField

fun TemplateDto.toScreenModel(): TemplateScreenModel {
    return TemplateScreenModel(
        name = name,
        fields = fields.map { it.toScreenModel() },
        description = description
    )
}

fun TemplateFieldDto.toScreenModel(): TemplateScreenModelField {
    return TemplateScreenModelField(
        type = type,
        name = name
    )
}