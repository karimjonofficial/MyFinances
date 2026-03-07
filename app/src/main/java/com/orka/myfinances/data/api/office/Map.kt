package com.orka.myfinances.data.api.office

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office

fun OfficeApiModel.map(company: Company): Office {
    return Office(
        id = Id(id),
        name = name,
        company = company,
        address = address,
        phone = phone
    )
}