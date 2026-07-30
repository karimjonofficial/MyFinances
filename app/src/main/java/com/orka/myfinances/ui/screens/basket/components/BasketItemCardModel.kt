package com.orka.myfinances.ui.screens.basket.components

data class BasketItemCardModel(
    val title: String,
    val properties: String?,
    val availableAmount: String,
    val description: String?,
    val price: String,
    val amount: String,
    val imageRes: Int,
    val increaseEnabled: Boolean,
    val decreaseEnabled: Boolean,
    val unavailable: Boolean
)
