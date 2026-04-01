package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.api.template.models.response.TemplateApiModelField
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModelField

fun TemplateApiModel.toScreenModel(): TemplateScreenModel {
    return TemplateScreenModel(
        name = name,
        fields = fields.map { it.toScreenModel() },
        description = description
    )
}

fun TemplateApiModelField.toScreenModel(): TemplateScreenModelField {
    return TemplateScreenModelField(
        type = type,
        name = name
    )
}