package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequest
import com.orka.myfinances.data.api.receive.models.request.AddReceiveApiRequestItem
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem

fun AddReceiveRequest.map(officeId: Int): AddReceiveApiRequest {
    return AddReceiveApiRequest(
        items = items.map { it.map() },
        officeId = officeId,
        price = price,
    )
}

fun AddReceiveRequestItem.map(): AddReceiveApiRequestItem {
    return AddReceiveApiRequestItem(
        productTitleId = productTitleId.value,
        price = price,
        salePrice = salePrice,
        amount = amount,
        description = description
    )
}