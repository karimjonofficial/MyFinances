package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.repositories.client.AddClientRequest

fun AddClientRequest.map(companyId: Int): AddClientApiRequest {
    return AddClientApiRequest(
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        address = address,
        phone = phone,
        companyId = companyId
    )
}