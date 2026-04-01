package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.request.AddDebtApiRequest
import com.orka.myfinances.data.repositories.debt.AddDebtRequest

fun AddDebtRequest.toApiRequest(officeId: Int): AddDebtApiRequest {
    return AddDebtApiRequest(
        clientId = clientId.value,
        officeId = officeId,
        price = price,
        description = description,
    )
}
