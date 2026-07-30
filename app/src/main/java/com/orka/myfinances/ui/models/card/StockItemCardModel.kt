package com.orka.myfinances.ui.models.card

data class StockItemCardModel(
    val title: String,
    val price: String,
    val amount: String,
    val properties: String?,
    val description: String?,
    val basketAmount: String? = null,
    val increaseEnabled: Boolean
)