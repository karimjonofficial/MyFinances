package com.orka.myfinances.ui.screens.home.components

import com.orka.myfinances.lib.ui.models.UiText

data class BasketItemCardModel(
    val title: String,
    val properties: String,
    val description: UiText,
    val price: String,
    val amount: String,
    val imageRes: Int
)
