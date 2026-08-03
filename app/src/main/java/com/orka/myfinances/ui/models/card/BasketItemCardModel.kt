package com.orka.myfinances.ui.models.card

data class BasketItemCardModel(
    val title: String,
    val properties: String?,
    val availableAmount: Int,
    val description: String?,
    val price: Int,
    val amount: Int,
    val imageRes: Int,
    val increaseEnabled: Boolean,
    val decreaseEnabled: Boolean,
    val unavailable: Boolean
)