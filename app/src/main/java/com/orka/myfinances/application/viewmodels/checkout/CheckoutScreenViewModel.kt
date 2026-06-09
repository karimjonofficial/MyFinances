package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.api.client.toApiRequest
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
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.order.OrderEvent
import com.orka.myfinances.data.repositories.order.toOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.data.repositories.sale.toSaleRequest
import com.orka.myfinances.lib.data.api.scoped.company.add
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
    private val clientApi: ClientApi,
    private val stockApi: StockApi,
    private val orderFlow: MutableSharedFlow<OrderEvent>,
    private val saleFlow: MutableSharedFlow<SaleEvent>,
    private val clientFlow: MutableSharedFlow<ClientEvent>,
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

    override fun sell(clientId: Id, price: Int?, description: String?, print: Boolean) =
        tryTransition { state ->
            if (performSell(clientId, price, description, print)) state
            else State.Failure(failure, state.value)
        }

    override fun sell(
        firstName: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?,
        price: Int?,
        description: String?,
        print: Boolean
    ) = tryTransition { state ->
        val id = createClient(firstName, lastName, patronymic, phone, address)
        if (id != null && performSell(id, price, description, print)) state
        else State.Failure(failure, state.value)
    }

    override fun order(clientId: Id, price: Int?, description: String?) =
        tryTransition { state ->
            if (performOrder(clientId, price, description)) state
            else State.Failure(failure, state.value)
        }

    override fun order(
        firstName: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?,
        price: Int?,
        description: String?
    ) = tryTransition { state ->
        val id = createClient(firstName, lastName, patronymic, phone, address)
        if (id != null && performOrder(id, price, description)) state
        else State.Failure(failure, state.value)
    }

    private suspend fun createClient(
        firstName: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?
    ): Id? {
        val response: ClientApiModel? = clientApi.add(
            request = AddClientRequest(firstName, lastName, patronymic, phone, address),
            map = AddClientRequest::toApiRequest
        )
        if (response != null) {
            clientFlow.emit(ClientEvent)
            return Id(response.id)
        }
        return null
    }

    private suspend fun performSell(
        clientId: Id,
        price: Int?,
        description: String?,
        print: Boolean
    ): Boolean {
        if (price == null) return false
        val items = getBasketItems(basketRepository.get(), stockApi)
        val basket = Basket(price, description, items)
        val response: SaleApiModel? = saleApi.add(
            request = basket.toSaleRequest(clientId),
            map = AddSaleRequest::toApiRequest
        )
        if (response != null) {
            if (print) printer.print(response)
            clearBasket()
            saleFlow.emit(SaleEvent)
            navigator.back()
            return true
        }
        return false
    }

    private suspend fun performOrder(
        clientId: Id,
        price: Int?,
        description: String?
    ): Boolean {
        if (price == null) return false
        val items = getBasketItems(basketRepository.get(), stockApi)
        val basket = Basket(price, description, items)
        val created = orderApi.insert(
            request = basket.toOrderRequest(clientId),
            map = AddOrderRequest::toApiRequest,
        )
        if (created) {
            clearBasket()
            orderFlow.emit(OrderEvent)
            navigator.back()
            return true
        }
        return false
    }

    private suspend fun clearBasket() {
        basketRepository.clear()
    }
}