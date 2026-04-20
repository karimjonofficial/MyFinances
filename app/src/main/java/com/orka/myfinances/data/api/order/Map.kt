package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.order.models.request.AddOrderApiRequest
import com.orka.myfinances.data.api.order.models.request.CompleteOrderApiRequest
import com.orka.myfinances.data.api.order.models.request.SetEndDateApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import kotlin.time.Instant

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

fun Id.toCompleteOrderApiRequest(): CompleteOrderApiRequest {
    return CompleteOrderApiRequest(
        id = value,
        completed = true
    )
}

fun Id.toSetEndDateApiRequest(endDateTime: Instant): SetEndDateApiRequest {
    return SetEndDateApiRequest(
        id = value,
        endDateTime = endDateTime
    )
}
