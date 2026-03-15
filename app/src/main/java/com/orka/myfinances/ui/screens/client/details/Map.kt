package com.orka.myfinances.ui.screens.client.details

import com.orka.myfinances.data.models.Client

fun Client.map(): ClientScreenModel {
    return ClientScreenModel(
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        phone = phone,
        address = address
    )
}