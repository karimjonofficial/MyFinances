package com.orka.myfinances.ui.screens.order.list

import com.orka.myfinances.lib.ui.models.UiText

data class OrderCardModel(
    val title: String,
    val price: String,
    val dateTime: UiText,
    val size: String,
    val completed: Boolean
)