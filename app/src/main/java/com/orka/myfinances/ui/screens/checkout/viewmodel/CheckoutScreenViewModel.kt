package com.orka.myfinances.ui.screens.checkout.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.ui.screens.client.viewmodel.map
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterState
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Instant

class CheckoutScreenViewModel(
    private val client: HttpClient,
    private val basketRepository: BasketRepository,
    private val navigator: Navigator,
    private val printer: Printer,
    private val printerState: StateFlow<PrinterState>,
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
            try {
                val response = client.get("clients/")
                if (response.status == HttpStatusCode.OK) {
                    val clientApiModels = response.body<List<ClientApiModel>>()
                    val clients = clientApiModels.map { it.map() }
                    setState(
                        CheckoutScreenState.Success(
                            clients = clients,
                            items = items.map { it.toModel(formatPrice, formatDecimal) },
                            price = items.getPrice(),
                            printerConnected = printerState.value is PrinterState.Connected
                        )
                    )
                } else setState(CheckoutScreenState.Failure)
            } catch (_: Exception) {
                setState(CheckoutScreenState.Failure)
            }
        }

        launch {
            printerState.collect { newState ->
                updateState { currentState ->
                    if (currentState is CheckoutScreenState.Success) {
                        currentState.copy(printerConnected = newState is PrinterState.Connected)
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    fun sell(client: Client?, price: Int?, description: String?, print: Boolean) {
        launch {
            if (client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    val saleResponse = this@CheckoutScreenViewModel.client.post(
                        urlString = "sales/",
                        block = { setBody(basket.toSaleRequest(client)) }
                    )
                    if (saleResponse.status == HttpStatusCode.Created) {
                        val response = saleResponse.body<Sale>()
                        if (print && printerState.value is PrinterState.Connected) {
                            printer.print(response)
                        }
                        clearBasket()
                        navigator.back()
                    }
                } catch (_: Exception) {
                    // Handle error
                }
            }
        }
    }

    fun order(client: Client?, price: Int?, description: String?) {
        launch {
            if (client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    val orderResponse = this@CheckoutScreenViewModel.client.post(
                        urlString = "orders/",
                        block = { setBody(basket.toOrderRequest(client)) }
                    )
                    if (orderResponse.status == HttpStatusCode.Created) {
                        clearBasket()
                        navigator.back()
                    }
                } catch (_: Exception) {
                    // Handle error
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
