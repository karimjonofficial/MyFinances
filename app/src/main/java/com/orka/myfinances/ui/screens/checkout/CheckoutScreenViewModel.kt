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
import com.orka.myfinances.ui.navigation.Navigator

class CheckoutScreenViewModel(
    private val basketRepository: BasketRepository,
    private val addSale: Add<Sale, AddSaleRequest>,
    private val addOrder: Add<Order, AddOrderRequest>,
    get: Get<Client>,
    private val navigator: Navigator,
    logger: Logger,
    loading: UiText,
    failure: UiText
) : ListViewModel<Client>(
    loading = loading,
    failure = failure,
    repository = get,
    logger = logger
) {
    fun sell(client: Client?, price: Int?, description: String?) {
        launch {
            if(client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                val response = addSale.add(basket.toSaleRequest(client))
                if (response != null) {
                    clearBasket()
                    navigator.back()
                }
            }
        }
    }

    fun order(client: Client?, price: Int?, description: String?) {
        launch {
            if(client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                val response = addOrder.add(basket.toOrderRequest(client))
                if (response != null) {
                    clearBasket()
                    navigator.back()
                }
            }
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