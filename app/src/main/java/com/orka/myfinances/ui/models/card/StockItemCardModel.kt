package com.orka.myfinances.ui.models.card

data class StockItemCardModel(
    val title: String,
    val price: Int,
    val amount: Int,
    val properties: String?,
    val description: String?,
    val basketAmount: Int? = null,
    val increaseEnabled: Boolean
)