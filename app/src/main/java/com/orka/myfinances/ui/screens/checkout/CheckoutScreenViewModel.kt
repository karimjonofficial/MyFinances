package com.orka.myfinances.ui.screens.checkout

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.AddSaleRequest
import com.orka.myfinances.data.repositories.SaleRepository
import kotlinx.coroutines.CoroutineScope

class CheckoutScreenViewModel(
    private val repository: SaleRepository,
    private val clearBasket: () -> Unit,
    coroutineScope: CoroutineScope
) : Manager(coroutineScope) {

    fun sell(basket: Basket, client: Client) {
        launch {
            val response = repository.add(AddSaleRequest(basket, client.id.value))
            if(response != null) clearBasket()
        }
    }
}
