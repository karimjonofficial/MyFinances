package com.orka.myfinances.ui.models.card

data class ClientCardModel(
    val shortName: String,
    val fullName: String,
    val phone: String? = null
)