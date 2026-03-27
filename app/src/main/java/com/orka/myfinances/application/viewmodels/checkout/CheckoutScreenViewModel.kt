package com.orka.myfinances.application.viewmodels.checkout

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFul
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.printer.PrinterState
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFul<State<CheckoutScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), CheckoutScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        printerState.onEach { newState ->
            updateState { currentState ->
                val successState = (currentState as? State.Success)?.value
                if (successState != null) {
                    State.Success(
                        value = successState.copy(
                            printerConnected = newState is PrinterState.Connected
                        )
                    )
                } else currentState
            }
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            val items = basketRepository.get()
            try {
                val clientApiModels = clientApi.getAll()
                if (clientApiModels != null) {
                    val clients = clientApiModels.map { it.toItemModel() }
                    setState(
                        State.Success(
                            value = CheckoutScreenModel(
                                clients = clients,
                                items = items.map { it.toModel(formatPrice, formatDecimal) },
                                price = items.getPrice().toInt(),
                                printerConnected = printerState.value is PrinterState.Connected
                            )
                        )
                    )
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun refresh() {
        launch {
            setState(State.Loading(loading))
            val items = basketRepository.get()
            try {
                val clientApiModels = clientApi.getAll()
                if (clientApiModels != null) {
                    val clients = clientApiModels.map { it.toItemModel() }
                    setState(
                        State.Success(
                            value = CheckoutScreenModel(
                                clients = clients,
                                items = items.map { it.toModel(formatPrice, formatDecimal) },
                                price = items.getPrice().toInt(),
                                printerConnected = printerState.value is PrinterState.Connected
                            )
                        )
                    )
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun sell(
        id: Id,
        price: Int?,
        description: String?,
        print: Boolean
    ) {
        launch {
            if (price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    val response = saleApi.add(basket.toSaleRequest(id))
                    if (response != null) {
                        if (print && printerState.value is PrinterState.Connected) {
                            printer.print(response)
                        }
                        clearBasket()
                        navigator.back()
                    } else setState(State.Failure(failure))
                } catch (e: Exception) {
                    setState(State.Failure(UiText.Str(e.message.toString())))
                }
            }
        }
    }

    override fun order(id: Id, price: Int?, description: String?) {
        launch {
            if (price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    if (orderApi.add(basket.toOrderRequest(id))) {
                        clearBasket()
                        navigator.back()
                    }
                } catch (e: Exception) {
                    setState(State.Failure(UiText.Str(e.message.toString())))
                }
            }
        }
    }

    private suspend fun clearBasket() {
        basketRepository.clear()
    }
}