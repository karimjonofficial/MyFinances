package com.orka.myfinances.ui.models.ui

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.models.card.ClientCardModel

data class ClientUiModel(
    val id: Id,
    val model: ClientCardModel
)