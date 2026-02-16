package com.orka.myfinances.ui.screens.clients

import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.components.ClientCardModel

fun Client.toModel(): ClientCardModel {
    return ClientCardModel(
        shortName = "${firstName[0]}${lastName?.firstOrNull() ?: ""}",
        fullName = "$firstName $lastName",
        phone = if (phone.isNullOrBlank()) UiText.Res(R.string.no_phone_number) else UiText.Str(
            phone
        )
    )
}