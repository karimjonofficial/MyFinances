package com.orka.myfinances.ui.screens.client.details

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.ui.models.screen.ClientScreenModel

fun Client.map(): ClientScreenModel {
    return ClientScreenModel(
        fullName = if (lastName.isNullOrEmpty()) firstName else "$firstName $lastName",
        phone = phone,
        address = address
    )
}