package com.orka.myfinances.ui.models.card

import com.orka.myfinances.lib.ui.models.UiText

data class StockItemCardModel(
    val title: String,
    val price: String,
    val amount: String,
    val properties: UiText,
    val description: UiText,
    val basketAmount: String? = null,
    val increaseEnabled: Boolean
)