package com.orka.myfinances.ui.screens.templates.details

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField

fun Template.map(): TemplateScreenModel {
    return TemplateScreenModel(
        name = name,
        fields = fields.map { it.map() },
        description = description
    )
}

fun TemplateField.map(): TemplateScreenModelField {
    return TemplateScreenModelField(
        type = type,
        name = name
    )
}