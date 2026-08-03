package com.orka.myfinances.ui.models.screen

import com.orka.myfinances.ui.models.item.CheckoutItemModel

data class CheckoutScreenModel(
    val items: List<CheckoutItemModel>,
    val exposedPrice: Int,
    val salePrice: Int
)