package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.request.AddDebtApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.AddDebtRequest

fun AddDebtRequest.toApiRequest(officeId: Id): AddDebtApiRequest {
    return AddDebtApiRequest(
        clientId = clientId.value,
        officeId = officeId.value,
        price = price,
        description = description,
    )
}
