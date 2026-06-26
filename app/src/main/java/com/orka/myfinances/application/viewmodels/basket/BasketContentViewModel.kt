package com.orka.myfinances.application.viewmodels.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.basket.getBasketItems
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
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
    private val repository: BasketRepository,
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
    produceSuccess = {
        val items = getBasketItems(repository.get(), stockRepository)
        val uiItems = items.map { item -> item.toUiModel(formatPrice, formatDecimal) }
        val sellable = uiItems.indexOfFirst { it.model.unavailable } == -1
        val price = items.sumOf { it.product.exposedPrice * it.amount }

        State.Success(
            value = BasketScreenModel(
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
        repository.events.onEach { event ->
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
                if(state.value is State.Loading && state.value.value == null)
                    initialize()
                else refresh()
                isStale = false
            }
        }.launchIn(viewModelScope)
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

    override fun increase(item: BasketItemUiModel) {
        launch {
            repository.add(id = item.productTitleId, amount = 1)
        }
    }

    override fun decrease(item: BasketItemUiModel) {
        launch {
            repository.remove(item.productTitleId, 1)
        }
    }

    override fun remove(item: BasketItemUiModel) {
        launch {
            repository.remove(item.productTitleId, item.amount)
        }
    }

    override fun clear() {
        launch {
            repository.clear()
        }
    }

    override fun checkout() {
        launch {
            if ((state.value as? State.Success)?.value != null) {
                navigator.navigateToCheckout()
            }
        }
    }
}
