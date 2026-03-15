package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.api.template.TemplateFieldApiModel
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModel
import com.orka.myfinances.ui.screens.templates.details.TemplateScreenModelField

fun TemplateApiModel.map(): TemplateScreenModel {
    return TemplateScreenModel(
        name = name,
        fields = fields.map { it.map() },
        description = description
    )
}

fun TemplateFieldApiModel.map(): TemplateScreenModelField {
    return TemplateScreenModelField(
        type = type,
        name = name
    )
}