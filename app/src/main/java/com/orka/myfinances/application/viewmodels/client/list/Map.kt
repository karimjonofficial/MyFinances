package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientModel

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