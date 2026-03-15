package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.ui.screens.templates.add.TemplateFieldModel

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

fun AddTemplateRequest.map(branch: Int): AddTemplateApiRequest {
    return AddTemplateApiRequest(
        name = name,
        branch = branch,
        fields = fields.map { it.map() }
    )
}

fun TemplateFieldModel.map(): AddTemplateApiRequestField {
    return AddTemplateApiRequestField(
        name = name,
        type = type
    )
}