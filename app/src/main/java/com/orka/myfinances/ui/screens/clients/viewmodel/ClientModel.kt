package com.orka.myfinances.ui.screens.clients.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel

data class ClientModel(
    val id: Id,
    val model: ClientCardModel
)