package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequest
import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequestItem
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem

fun AddReceiveRequest.toApiRequest(office: Office): AddReceiveApiRequest {
    return AddReceiveApiRequest(
        items = items.map { it.toApiRequest() },
        officeId = office.id.value,
        price = price,
    )
}

fun AddReceiveRequestItem.toApiRequest(): AddReceiveApiRequestItem {
    return AddReceiveApiRequestItem(
        productTitleId = productTitleId.value,
        price = price,
        salePrice = salePrice,
        amount = amount,
        description = description
    )
}