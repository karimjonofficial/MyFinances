package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientUiModel

fun ClientApiModel.toUiModel(): ClientUiModel {
    return ClientUiModel(
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