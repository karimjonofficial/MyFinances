package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequest
import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequestField
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.ui.screens.templates.add.TemplateFieldModel

fun AddTemplateRequest.toApiRequest(office: Office): AddTemplateApiRequest {
    return AddTemplateApiRequest(
        name = name,
        branch = office.id.value,
        fields = fields.map { it.toApiRequest() }
    )
}

fun TemplateFieldModel.toApiRequest(): AddTemplateApiRequestField {
    return AddTemplateApiRequestField(
        name = name,
        type = type
    )
}