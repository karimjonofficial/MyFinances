package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequest
import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequestField
import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.api.template.models.response.TemplateApiModelField
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.dtos.template.TemplateFieldDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.requests.TemplateFieldModel

fun TemplateApiModel.toDto(): TemplateDto {
    return TemplateDto(
        id = id,
        name = name,
        fields = fields.map { it.toDto() },
        description = description,
    )
}

fun TemplateApiModelField.toDto(): TemplateFieldDto {
    return TemplateFieldDto(
        id = id,
        name = name,
        type = type,
    )
}

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
