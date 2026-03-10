package com.orka.myfinances.application.manager

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.application.models.CompanyApiModel

fun CompanyApiModel.map(): Company {
    return Company(
        id = Id(id),
        name = name,
        address = address,
        phone = phone
    )
}