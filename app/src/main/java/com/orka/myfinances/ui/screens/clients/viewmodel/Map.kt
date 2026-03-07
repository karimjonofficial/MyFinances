package com.orka.myfinances.ui.screens.clients.viewmodel

import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel

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

fun ClientApiModel.toUiModel(): ClientModel {
    return ClientModel(
        id = Id(id),
        model = map()
    )
}

fun ClientApiModel.map(): ClientCardModel {
    return ClientCardModel(
        shortName = "${firstName[0]}${lastName?.firstOrNull() ?: ""}",
        fullName = "$firstName $lastName",
        phone = phone,
    )
}