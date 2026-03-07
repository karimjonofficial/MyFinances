package com.orka.myfinances.ui.screens.client.viewmodel

import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id

fun ClientApiModel.map(): Client {
    return Client(
        id = Id(id),
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        address = address
    )
}