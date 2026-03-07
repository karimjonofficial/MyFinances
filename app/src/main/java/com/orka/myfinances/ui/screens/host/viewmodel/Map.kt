package com.orka.myfinances.ui.screens.host.viewmodel

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel

fun Office.toUiModel(): OfficeUiModel {
    return OfficeUiModel(
        name = name,
        office = this
    )
}

fun CompanyApiModel.map(): Company {
    return Company(
        id = Id(id),
        name = name,
        address = address,
        phone = phone
    )
}