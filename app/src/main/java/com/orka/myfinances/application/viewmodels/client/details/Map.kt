package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.client.details.ClientScreenModel
import com.orka.myfinances.ui.models.ClientItemModel

fun ClientDto.toItemModel(): ClientItemModel {
    return ClientItemModel(
        id = Id(id),
        title = if (lastName.isNullOrEmpty()) firstName else "$firstName $lastName"
    )
}

fun ClientDto.toScreenModel(): ClientScreenModel {
    return ClientScreenModel(
        fullName = if (lastName.isNullOrEmpty()) firstName else "$firstName $lastName",
        phone = phone,
        address = address
    )
}
