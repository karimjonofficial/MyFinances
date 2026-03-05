package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office

fun Office.toUiModel(): OfficeUiModel {
    return OfficeUiModel(
        name = name,
        office = this
    )
}

fun CompanyModel.map(): Company {
    return Company(
        id = Id(id),
        name = name,
        address = address,
        phone = phone
    )
}