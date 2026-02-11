package com.orka.myfinances.ui.screens.checkout

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class CheckoutScreenViewModel(
    private val saleRepository: Add<Sale, AddSaleRequest>,
    private val orderRepository: Add<Order, AddOrderRequest>,
    private val basketRepository: BasketRepository,
    clientRepository: Get<Client>,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Client>(
    loading = loading,
    failure = failure,
    repository = clientRepository,
    logger = logger
) {
    fun sell(basket: Basket, client: Client) {
        launch {
            val response = saleRepository.add(basket.toSaleRequest(client))
            if(response != null) clearBasket()
        }
    }

    fun order(basket: Basket, client: Client) {
        launch {
            val response = orderRepository.add(basket.toOrderRequest(client))
            if(response != null) clearBasket()
        }
    }

    private suspend fun clearBasket() {
        basketRepository.clear()
    }
    private fun Basket.toOrderRequest(client: Client): AddOrderRequest {
        return AddOrderRequest(
            client = client.id.value,
            items = items.map { it.toItem() },
            price = price,
            description = description
        )
    }
    private fun Basket.toSaleRequest(client: Client): AddSaleRequest {
        return AddSaleRequest(
            clientId = client.id.value,
            items = items.map { it.toItem() },
            price = price,
            description = description
        )
    }
    private fun BasketItem.toItem(): Item {
        return Item(product.id.value, amount)
    }
}