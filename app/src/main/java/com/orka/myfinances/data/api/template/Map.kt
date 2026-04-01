package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequest
import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequestField
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.ui.screens.templates.add.TemplateFieldModel

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