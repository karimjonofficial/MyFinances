package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

data class CheckoutScreenModel(
    val clients: List<ClientItemModel>,
    val items: List<BasketItemCardModel>,
    val price: Int,
    val printerConnected: Boolean = false
)