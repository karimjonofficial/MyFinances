package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField

fun TemplateFieldApiModel.map(): TemplateField {
    return TemplateField(
        id = Id(id),
        name = name,
        type = type
    )
}

fun TemplateApiModel.map(): Template {
    return Template(
        id = Id(id),
        name = name,
        fields = fields.map { it.map() }
    )
}