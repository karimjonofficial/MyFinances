package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.request.AddDebtApiRequest
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.debt.AddDebtRequest

fun AddDebtRequest.toApiRequest(office: Office): AddDebtApiRequest {
    return AddDebtApiRequest(
        clientId = clientId.value,
        officeId = office.id.value,
        price = price,
        description = description,
    )
}
