package com.orka.myfinances.ui.screens.checkout.viewmodel

data class CheckoutScreenModel(
    val items: List<BasketItemCardModel>,
    val exposedPrice: Int,
    val salePrice: String
)