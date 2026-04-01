package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.order.models.request.AddOrderApiRequest
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.order.AddOrderRequest

fun AddOrderRequest.toApiRequest(office: Office): AddOrderApiRequest {
    return AddOrderApiRequest(
        clientId = clientId.value,
        branchId = office.id.value,
        items = items,
        price = price,
        endDateTime = endDateTime,
        description = description
    )
}