package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.application.viewmodels.client.details.map
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterState
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutScreenViewModel(
    private val clientApi: ClientApi,
    private val saleApi: SaleApi,
    private val orderApi: OrderApi,
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
), CheckoutScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val items = basketRepository.get()
            try {
                val clientApiModels = clientApi.getAll()
                if (clientApiModels != null) {
                    val clients = clientApiModels.map { it.map() }
                    setState(
                        CheckoutScreenState.Success(
                            clients = clients,
                            items = items.map { it.toModel(formatPrice, formatDecimal) },
                            price = items.getPrice().toInt(),//TODO
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
                    } else currentState
                }
            }
        }
    }

    override fun sell(
        client: Client?,
        price: Int?,
        description: String?,
        print: Boolean
    ) {
        launch {
            if (client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    val response = saleApi.add(basket.toSaleRequest(client.id))
                    if (response != null) {
                        if (print && printerState.value is PrinterState.Connected) {
                            printer.print(response)
                        }
                        clearBasket()
                        navigator.back()
                    }
                } catch (_: Exception) { }
            }
        }
    }

    override fun order(client: Client?, price: Int?, description: String?) {
        launch {
            if (client != null && price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    if (orderApi.add(basket.toOrderRequest(client.id))) {
                        clearBasket()
                        navigator.back()
                    }
                } catch (_: Exception) {
                    // Handle error
                }
            }
        }
    }

    private suspend fun clearBasket() {
        basketRepository.clear()
    }
}