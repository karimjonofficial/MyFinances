package com.orka.myfinances.ui.components

import com.orka.myfinances.lib.ui.models.UiText

data class ClientCardModel(
    val shortName: String,
    val fullName: String,
    val phone: UiText
)