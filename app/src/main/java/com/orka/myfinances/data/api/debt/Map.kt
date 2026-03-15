package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.repositories.debt.AddDebtRequest

fun AddDebtRequest.map(): AddDebtApiRequest {
    return AddDebtApiRequest(
        clientId = clientId.value,
        price = price,
        endDateTime = endDateTime,
        description = description
    )
}