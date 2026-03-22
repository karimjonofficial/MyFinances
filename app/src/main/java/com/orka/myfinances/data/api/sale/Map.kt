package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.api.sale.models.request.AddSaleApiRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest

fun AddSaleRequest.map(officeId: Int): AddSaleApiRequest {
    return AddSaleApiRequest(
        clientId = clientId.value,
        items = items,
        price = price,
        officeId = officeId,
        description = description
    )
}