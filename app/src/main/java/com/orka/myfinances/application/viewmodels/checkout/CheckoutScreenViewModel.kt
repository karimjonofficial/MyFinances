package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.getBasketItems
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.order.toOrderRequest
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.sale.toSaleRequest
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlin.time.Instant

class CheckoutScreenViewModel(
    private val repository: SaleRepository,
    private val orderRepository: OrderRepository,
    private val debtRepository: DebtRepository,
    private val stockRepository: GetStockItemByProduct,
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
        val items = getBasketItems(minItems, stockRepository)

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

    override fun sell(
        clientId: Id,
        price: Int?,
        description: String?,
        print: Boolean
    ) {
        tryTransition { state ->
            if (price != null) {
                val items = getBasketItems(basketRepository.get(), stockRepository)
                val basket = Basket(price, description, items)
                val response = repository.add(basket.toSaleRequest(clientId))

                if (response != null) {
                    if (print) printer.print(response)
                    basketRepository.clear()
                    navigator.back()
                    state
                } else State.Failure(failure, state.value)
            } else state
        }
    }

    override fun debt(
        clientId: Id,
        price: Int?,
        description: String?,
        print: Boolean,
        dueDate: LocalDate
    ) {
        tryTransition { state ->
            if (price != null) {
                val items = getBasketItems(basketRepository.get(), stockRepository)
                val basket = Basket(price, description, items)
                val response = repository.add(basket.toSaleRequest(clientId))

                if (response != null) {
                    if (print) printer.print(response)
                    basketRepository.clear()
                    val debtRequest = AddDebtRequest(
                        clientId = clientId,
                        price = price,
                        description = if (description != null) "$description\nSale id: ${response.id}" else "Sale id: ${response.id}",
                        endDateTime = Instant.fromEpochMilliseconds(
                            dueDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                        )
                    )
                    val created = debtRepository.insert(debtRequest)
                    if (created) {
                        navigator.back()
                        state
                    } else State.Failure(failure, state.value)
                } else State.Failure(failure, state.value)
            } else state
        }
    }

    override fun order(
        clientId: Id,
        price: Int?,
        description: String?,
        endDate: LocalDate
    ) {
        tryTransition { state ->
            if (price != null) {
                val items = getBasketItems(basketRepository.get(), stockRepository)
                val basket = Basket(price, description, items)
                val date = Instant.fromEpochMilliseconds(
                    endDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                )
                val created = orderRepository.insert(basket.toOrderRequest(clientId, date))

                if (created) {
                    basketRepository.clear()
                    navigator.back()
                    state
                } else State.Failure(failure, state.value)
            } else state
        }
    }
}
