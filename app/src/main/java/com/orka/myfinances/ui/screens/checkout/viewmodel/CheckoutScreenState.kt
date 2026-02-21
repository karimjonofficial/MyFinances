package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.data.models.Client

interface CheckoutScreenState {
    object Loading : CheckoutScreenState
    object Failure : CheckoutScreenState

    data class Success(
        val clients: List<Client>,
        val items: List<BasketItemCardModel>,
        val price: Int
    ) : CheckoutScreenState
}