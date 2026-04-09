package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.toApiRequest
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.api.sale.toApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.lib.data.api.scoped.office.add
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import kotlinx.coroutines.flow.asStateFlow

class CheckoutScreenViewModel(
    private val saleApi: SaleApi,
    private val orderApi: OrderApi,
    private val basketRepository: BasketRepository,
    private val navigator: Navigator,
    private val printer: Printer,
    private val formatDecimal: FormatDecimal,
    private val formatPrice: FormatPrice,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<CheckoutScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val items = basketRepository.get()
        State.Success(
            value = CheckoutScreenModel(
                items = items.map { it.toModel(formatPrice, formatDecimal) },
                price = items.getPrice().toInt(),
            )
        )
    },
    logger = logger
), CheckoutScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun sell(
        clientId: Id,
        price: Int?,
        description: String?,
        print: Boolean
    ) {
        launch {
            try {
                if (price != null) {
                    setState(State.Loading(loading))
                    val items = basketRepository.get()
                    val basket = Basket(price, description, items)
                    val response: SaleApiModel? = saleApi.add(
                        request = basket.toSaleRequest(clientId),
                        map = AddSaleRequest::toApiRequest
                    )
                    if (response != null) {
                        if (print) printer.print(response)
                        clearBasket()
                        navigator.back()
                    } else setState(State.Failure(failure))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun order(clientId: Id, price: Int?, description: String?) {
        launch {
            if (price != null) {
                val items = basketRepository.get()
                val basket = Basket(price, description, items)
                try {
                    val created = orderApi.insert(
                        request = basket.toOrderRequest(clientId),
                        map = AddOrderRequest::toApiRequest,
                    )
                    if (created) {
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
