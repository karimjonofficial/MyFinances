package com.orka.myfinances.ui.screens.clients.viewmodel

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.ui.components.ClientCardModel

data class ClientModel(
    val client: Client,
    val model: ClientCardModel
)