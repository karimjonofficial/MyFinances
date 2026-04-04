package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.client.details.ClientScreenModel
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

fun ClientApiModel.toItemModel(): ClientItemModel {
    return ClientItemModel(
        id = Id(id),
        title = "$firstName $lastName"
    )
}

fun ClientApiModel.toScreenModel(): ClientScreenModel {
    return ClientScreenModel(
        fullName = if(lastName.isNullOrEmpty()) firstName else "$firstName $lastName",
        phone = phone,
        address = address
    )
}