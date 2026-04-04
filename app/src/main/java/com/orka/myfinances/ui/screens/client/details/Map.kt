package com.orka.myfinances.ui.screens.client.details

import com.orka.myfinances.data.models.Client

fun Client.map(): ClientScreenModel {
    return ClientScreenModel(
        fullName = if(lastName.isNullOrEmpty()) firstName else "$firstName $lastName",
        phone = phone,
        address = address
    )
}