package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.api.order.OrderApi
import com.orka.myfinances.data.api.order.toApiRequest
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.api.sale.toApiRequest
import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.getBasketItems
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.data.repositories.order.toOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.data.repositories.sale.toSaleRequest
import com.orka.myfinances.lib.data.api.scoped.office.add
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.extensions.models.getExposedPrice
import com.orka.myfinances.lib.extensions.models.getSalePrice
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutScreenViewModel(
    private val saleApi: SaleApi,
    private val orderApi: OrderApi,
    private val stockApi: StockApi,
    private val orderFlow: MutableSharedFlow<OrderEvent>,
    private val saleFlow: MutableSharedFlow<SaleEvent>,
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
        val minItems = basketRepository.get()
        val items = getBasketItems(minItems, stockApi)

        State.Success(
            value = CheckoutScreenModel(
                items = items.map { it.toModel(formatPrice, formatDecimal) },
                exposedPrice = items.getExposedPrice().toInt(),
                salePrice = formatPrice.formatPrice(items.getSalePrice().toDouble())
            )
        )
    },
    logger = logger
), CheckoutScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun sell(clientId: Id, price: Int?, description: String?, print: Boolean) {
        tryTransition { state ->
            if (price != null) {
                val items = getBasketItems(basketRepository.get(), stockApi)
                val basket = Basket(price, description, items)
                val response: SaleApiModel? = saleApi.add(
                    request = basket.toSaleRequest(clientId),
                    map = AddSaleRequest::toApiRequest
                )

                if (response != null) {
                    if (print) printer.print(response)
                    basketRepository.clear()
                    saleFlow.emit(SaleEvent)
                    navigator.back()
                    state
                } else State.Failure(failure, state.value)
            } else state
        }
    }

    override fun order(clientId: Id, price: Int?, description: String?) {
        tryTransition { state ->
            if (price != null) {
                val items = getBasketItems(basketRepository.get(), stockApi)
                val basket = Basket(price, description, items)
                val created = orderApi.insert(
                    request = basket.toOrderRequest(clientId),
                    map = AddOrderRequest::toApiRequest,
                )

                if (created) {
                    basketRepository.clear()
                    orderFlow.emit(OrderEvent)
                    navigator.back()
                    state
                } else State.Failure(failure, state.value)
            } else state
        }
    }
}