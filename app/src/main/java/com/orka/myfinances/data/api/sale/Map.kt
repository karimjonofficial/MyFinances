package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.api.sale.models.request.AddSaleApiRequest
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.sale.AddSaleRequest

fun AddSaleRequest.toApiRequest(office: Office): AddSaleApiRequest {
    return AddSaleApiRequest(
        clientId = clientId.value,
        items = items,
        price = price,
        officeId = office.id.value,
        description = description
    )
}