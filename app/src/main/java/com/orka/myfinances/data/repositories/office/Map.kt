package com.orka.myfinances.data.repositories.office

import com.orka.myfinances.data.api.office.OfficeApiModel
import com.orka.myfinances.data.dtos.office.OfficeDto

fun OfficeApiModel.toDto(): OfficeDto {
    return OfficeDto(
        id = id,
        companyId = companyId,
        name = name,
        address = address,
        phone = phone
    )
}
