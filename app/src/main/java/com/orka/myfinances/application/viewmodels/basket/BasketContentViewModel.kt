package com.orka.myfinances.application.viewmodels.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.basket.BasketInteractor
import com.orka.myfinances.ui.screens.basket.BasketItemUiModel
import com.orka.myfinances.ui.screens.basket.BasketScreenModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BasketContentViewModel(
    private val basketRepository: BasketRepository,
    private val stockRepository: GetStockItemByProduct,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<BasketScreenModel>(
    loading = loading,
    failure = failure,
    produceInitialState = {
        val minItems = basketRepository.get()
        val items = minItems.map { minItem ->
            val stockItem = stockRepository.getByProduct(minItem.id)
            if (stockItem != null)
                basketItem(minItem, stockItem)
            else throw Exception()
        }
        val uiItems = items.map { item -> item.toUiModel(formatPrice, formatDecimal) }
        val sellable = uiItems.indexOfFirst { it.model.unavailable } == -1
        val price = items.sumOf { it.product.exposedPrice * it.amount }

        State.Success(
            BasketScreenModel(
                items = uiItems,
                price = formatPrice.formatPrice(price.toDouble()),
                rawItems = items,
                sellable = sellable,
            )
        )
    },
    logger = logger
), BasketInteractor {
    val uiState = state.asStateFlow()
    private var isStale = true

    init {
        basketRepository.events.onEach { event ->
            if (state.subscriptionCount.value > 0) {
                when (event) {
                    is BasketEvent.AmountChanged -> updateAmountLocally(event)
                    is BasketEvent.ItemRemoved -> removeItemLocally(event)
                    is BasketEvent.Clear -> clearLocally()
                    is BasketEvent.FullRefresh -> refresh()
                }
                isStale = false
            } else isStale = true
        }.launchIn(viewModelScope)

        state.subscriptionCount.onEach { count ->
            if (count > 0 && isStale) {
                if (state.value is State.Loading && state.value.value == null)
                    initialize()
                else refresh()
                isStale = false
            }
        }.launchIn(viewModelScope)
    }

    override fun increase(item: BasketItemUiModel) {
        launch {
            basketRepository.add(id = item.productId, amount = 1)
        }
    }

    override fun decrease(item: BasketItemUiModel) {
        launch {
            basketRepository.remove(item.productId, 1)
        }
    }

    override fun remove(item: BasketItemUiModel) {
        launch {
            basketRepository.remove(item.productId, item.amount)
        }
    }

    override fun clear() {
        launch {
            basketRepository.clear()
        }
    }

    override fun checkout() {
        launch {
            if ((state.value as? State.Success)?.value != null) {
                navigator.navigateToCheckout()
            }
        }
    }

    private fun updateAmountLocally(event: BasketEvent.AmountChanged) {
        tryTransition { oldState ->
            val oldModel = oldState.value ?: return@tryTransition refreshAndReturn(oldState)
            val updatedRawItems = oldModel.rawItems.map { item ->
                if (item.product.id == event.id.value) {
                    item.copy(
                        amount = event.newAmount,
                        increaseEnabled = event.newAmount < item.availableAmount
                    )
                } else item
            }
            createSuccessState(updatedRawItems)
        }
    }

    private fun removeItemLocally(event: BasketEvent.ItemRemoved) {
        tryTransition { oldState ->
            val oldModel = oldState.value ?: return@tryTransition refreshAndReturn(oldState)
            val updatedRawItems = oldModel.rawItems.filter { it.product.id != event.id.value }
            createSuccessState(updatedRawItems)
        }
    }

    private fun clearLocally() {
        tryTransition {
            createSuccessState(emptyList())
        }
    }

    private fun refreshAndReturn(oldState: State<BasketScreenModel>): State<BasketScreenModel> {
        refresh()
        return oldState
    }

    private fun createSuccessState(rawItems: List<BasketItem>): State.Success<BasketScreenModel> {
        val uiItems = rawItems.map { item -> item.toUiModel(formatPrice, formatDecimal) }
        val sellable = uiItems.indexOfFirst { it.model.unavailable } == -1
        val price = rawItems.sumOf { it.product.exposedPrice * it.amount }
        return State.Success(
            value = BasketScreenModel(
                items = uiItems,
                price = formatPrice.formatPrice(price.toDouble()),
                rawItems = rawItems,
                sellable = sellable
            )
        )
    }
}
