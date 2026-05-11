package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequest
import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequestItem
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem

fun AddReceiveRequest.toApiRequest(officeId: Id): AddReceiveApiRequest {
    return AddReceiveApiRequest(
        items = items.map { it.toApiRequest() },
        officeId = officeId.value,
        price = price,
    )
}

fun AddReceiveRequestItem.toApiRequest(): AddReceiveApiRequestItem {
    return AddReceiveApiRequestItem(
        productTitleId = productTitleId.value,
        price = price,
        salePrice = salePrice,
        amount = amount,
        exposedPrice = exposedPrice,
        description = description
    )
}