package com.orka.myfinances.data.repositories.client

import com.orka.myfinances.data.api.client.models.request.AddClientApiRequest
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id

fun AddClientRequest.toApiRequest(companyId: Id): AddClientApiRequest {
    return AddClientApiRequest(
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        address = address,
        phone = phone,
        companyId = companyId.value
    )
}

fun ClientApiModel.toDto(): ClientDto {
    return ClientDto(
        id = id,
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        phone = phone,
        address = address
    )
}
