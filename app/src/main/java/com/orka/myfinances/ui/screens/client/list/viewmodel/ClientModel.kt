package com.orka.myfinances.ui.screens.client.list.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.components.ClientCardModel

data class ClientModel(
    val id: Id,
    val model: ClientCardModel
)