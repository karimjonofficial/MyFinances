package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.application.viewmodels.basket.basketItem
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.order.toOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.sale.toSaleRequest
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.extensions.models.getExposedPrice
import com.orka.myfinances.lib.extensions.models.getSalePrice
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.models.screen.CheckoutScreenModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.statuses.failure.failure
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlin.time.Instant

class CheckoutScreenViewModel(
    private val addSale: Add<SaleDto, AddSaleRequest>,
    private val insertOrder: Insert<AddOrderRequest>,
    private val insertDebt: Insert<AddDebtRequest>,
    private val stockRepository: GetStockItemByProduct,
    private val basketRepository: BasketRepository,
    private val navigator: Navigator,
    private val printer: Printer,
    logger: Logger
) : RefreshableBaseViewModel<CheckoutScreenModel>(
    produceInitialState = {
        val minItems = basketRepository.get()
        val items = minItems.map { minItem ->
            val stockItem = stockRepository.getByProduct(minItem.id)
            if (stockItem != null)
                basketItem(minItem, stockItem)
            else throw Exception()
        }

        State.Success(
            CheckoutScreenModel(
                items = items.map { it.toModel() },
                exposedPrice = items.getExposedPrice().toInt(),
                salePrice = items.getSalePrice().toInt()
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
                val minItems = basketRepository.get()
                val items = minItems.map { minItem ->
                    val stockItem = stockRepository.getByProduct(minItem.id)
                    if (stockItem != null)
                        basketItem(minItem, stockItem)
                    else throw Exception()
                }
                val basket = Basket(price, description, items)
                val response = addSale.add(basket.toSaleRequest(clientId))

                if (response != null) {
                    if (print) printer.printSaleReceipt(response)
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
                val minItems = basketRepository.get()
                val items = minItems.map { minItem ->
                    val stockItem = stockRepository.getByProduct(minItem.id)
                    if (stockItem != null)
                        basketItem(minItem, stockItem)
                    else throw Exception()
                }
                val basket = Basket(price, description, items)
                val response = addSale.add(basket.toSaleRequest(clientId))

                if (response != null) {
                    if (print) printer.printSaleReceipt(response)
                    basketRepository.clear()
                    val debtRequest = AddDebtRequest(
                        clientId = clientId,
                        price = price,
                        description = if (description != null) "$description\nSale id: ${response.id}" else "Sale id: ${response.id}",
                        endDateTime = Instant.fromEpochMilliseconds(
                            dueDate.atStartOfDayIn(TimeZone.currentSystemDefault())
                                .toEpochMilliseconds()
                        )
                    )
                    val created = insertDebt.insert(debtRequest)
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
                val minItems = basketRepository.get()
                val items = minItems.map { minItem ->
                    val stockItem = stockRepository.getByProduct(minItem.id)
                    if (stockItem != null)
                        basketItem(minItem, stockItem)
                    else throw Exception()
                }
                val basket = Basket(price, description, items)
                val date = Instant.fromEpochMilliseconds(
                    endDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                )
                val created = insertOrder.insert(basket.toOrderRequest(clientId, date))

                if (created) {
                    basketRepository.clear()
                    navigator.back()
                    state
                } else State.Failure(failure, state.value)
            } else state
        }
    }
}
