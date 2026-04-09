package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequest
import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequestField
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel

fun AddTemplateRequest.toApiRequest(officeId: Id): AddTemplateApiRequest {
    return AddTemplateApiRequest(
        name = name,
        branch = officeId.value,
        fields = fields.map { it.toApiRequest() }
    )
}

fun TemplateFieldModel.toApiRequest(): AddTemplateApiRequestField {
    return AddTemplateApiRequestField(
        name = name,
        type = type
    )
}