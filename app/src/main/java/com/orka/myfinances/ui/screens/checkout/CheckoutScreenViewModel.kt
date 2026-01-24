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
import com.orka.myfinances.lib.data.repositories.AddRepository
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.CoroutineScope

class CheckoutScreenViewModel(
    private val saleRepository: AddRepository<Sale, AddSaleRequest>,
    private val orderRepository: AddRepository<Order, AddOrderRequest>,
    private val basketRepository: BasketRepository,
    clientRepository: GetRepository<Client>,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<Unit, Client, Unit>(
    loading = Unit,
    failure = Unit,
    repository = clientRepository,
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