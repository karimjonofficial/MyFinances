package com.orka.myfinances.ui.models.card

data class UserCardModel(
    val shortName: String,
    val fullName: String,
    val phone: String? = null
)