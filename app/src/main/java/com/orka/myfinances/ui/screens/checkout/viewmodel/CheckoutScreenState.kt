package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.ui.screens.debt.list.ClientItemModel

interface CheckoutScreenState {
    object Loading : CheckoutScreenState
    object Failure : CheckoutScreenState

    data class Success(
        val clients: List<ClientItemModel>,
        val items: List<BasketItemCardModel>,
        val price: Int,
        val printerConnected: Boolean = false
    ) : CheckoutScreenState
}