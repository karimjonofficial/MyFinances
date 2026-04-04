package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.api.client.models.request.AddClientApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.client.AddClientRequest

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