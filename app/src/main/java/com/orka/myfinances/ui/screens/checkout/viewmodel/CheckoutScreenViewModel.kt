package com.orka.myfinances.ui.screens.checkout.viewmodel

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
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Instant

class CheckoutScreenViewModel(
    private val basketRepository: BasketRepository,
    private val addSale: Add<Sale, AddSaleRequest>,
    private val addOrder: Add<Order, AddOrderRequest>,
    private val get: Get<Client>,
    private val navigator: Navigator,
    private val formatDecimal: FormatDecimal,
    private val formatPrice: FormatPrice,
    logger: Logger
) : SingleStateViewModel<CheckoutScreenState>(
    initialState = CheckoutScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val items = basketRepository.get()
            val clients = get.get()
            if (clients != null)
                setState(CheckoutScreenState.Success(clients, items.map { it.toModel(formatPrice, formatDecimal) }, items.getPrice()))
            else setState(CheckoutScreenState.Failure)
        }
    }

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

    private fun Basket.toOrderRequest(
        client: Client,
        endDateTime: Instant? = null
    ): AddOrderRequest {
        return AddOrderRequest(
            clientId = client.id,
            items = items.map { it.toItem() },
            price = price,
            endDateTime = endDateTime,
            description = description
        )
    }

    private suspend fun clearBasket() {
        basketRepository.clear()
    }
    private fun Basket.toSaleRequest(client: Client): AddSaleRequest {
        return AddSaleRequest(
            clientId = client.id,
            items = items.map { it.toItem() },
            price = price,
            description = description
        )
    }
    private fun BasketItem.toItem(): Item {
        return Item(product.id, amount)
    }
}