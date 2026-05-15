package com.orka.myfinances.ui.screens.order.list.completed

import com.orka.myfinances.lib.ui.models.UiText

data class HistoryOrderCardModel(
    val title: String,
    val count: String,
    val price: String,
    val dateTime: UiText
)