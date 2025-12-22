package com.orka.myfinances.ui.screens.checkout

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.Item
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.data.repositories.order.OrderRepository
import kotlinx.coroutines.CoroutineScope

class CheckoutScreenViewModel(
    private val saleRepository: SaleRepository,
    private val orderRepository: OrderRepository,
    private val basketRepository: BasketRepository,
    clientRepository: ClientRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<Unit, Client, Unit>(
    loading = Unit,
    failure = Unit,
    getRepository = clientRepository,
    logger = logger,
    coroutineScope = coroutineScope
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

    fun clearBasket() {
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