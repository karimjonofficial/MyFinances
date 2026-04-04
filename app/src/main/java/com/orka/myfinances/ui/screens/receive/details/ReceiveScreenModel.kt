package com.orka.myfinances.ui.screens.receive.details

import com.orka.myfinances.ui.components.UserCardModel

data class ReceiveScreenModel(
    val user: UserCardModel,
    val price: String,
    val dateTime: String,
    val items: List<ReceiveItemModel>,
    val description: String? = null
)