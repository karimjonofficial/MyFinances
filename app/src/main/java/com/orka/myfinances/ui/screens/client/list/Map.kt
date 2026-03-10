package com.orka.myfinances.ui.screens.client.list

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientModel

fun Client.toModel(): ClientCardModel {
    return ClientCardModel(
        shortName = "${firstName[0]}${lastName?.firstOrNull() ?: ""}",
        fullName = "$firstName $lastName",
        phone = phone
    )
}

fun Client.toUiModel(): ClientModel {
    return ClientModel(
        id = id,
        model = toModel()
    )
}