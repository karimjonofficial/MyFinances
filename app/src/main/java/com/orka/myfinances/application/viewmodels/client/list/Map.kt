package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ClientCardModel
import com.orka.myfinances.ui.models.ui.ClientUiModel

fun ClientDto.toUiModel(): ClientUiModel {
    return ClientUiModel(
        id = Id(id),
        model = map()
    )
}

fun ClientDto.map(): ClientCardModel {
    return ClientCardModel(
        shortName = "${firstName[0]}${lastName?.firstOrNull() ?: ""}",
        fullName = "$firstName ${lastName ?: ""}",
        phone = phone,
    )
}
