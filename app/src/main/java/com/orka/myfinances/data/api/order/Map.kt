package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.order.models.request.AddOrderApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.AddOrderRequest

fun AddOrderRequest.toApiRequest(officeId: Id): AddOrderApiRequest {
    return AddOrderApiRequest(
        clientId = clientId.value,
        branchId = officeId.value,
        items = items,
        price = price,
        endDateTime = endDateTime,
        description = description
    )
}