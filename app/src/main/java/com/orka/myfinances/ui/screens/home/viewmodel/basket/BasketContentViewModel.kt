package com.orka.myfinances.ui.screens.home.viewmodel.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.components.BasketItemCardModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BasketContentViewModel(
    private val repository: BasketRepository,
    private val navigator: Navigator,
    private val priceFormatter: FormatPrice,
    private val decimalFormatter: FormatDecimal,
    logger: Logger
) : SingleStateViewModel<BasketState>(
    initialState = BasketState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        repository.events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        val items = repository.get()
        val price = items.sumOf { it.product.salePrice * it.amount }
        setState(
            BasketState.Success(
                items = items.map { item ->
                    BasketItemUiModel(
                        item = item,
                        model = item.toModel(priceFormatter, decimalFormatter)
                    )
                },
                price = priceFormatter.formatPrice(price.toDouble())
            )
        )
    }

    fun increase(item: BasketItem) {
        launch {
            repository.add(id = item.product.id, amount = 1)
        }
    }

    fun decrease(item: BasketItem) {
        launch {
            repository.remove(item.product.id, 1)
        }
    }

    fun remove(item: BasketItem) {
        launch {
            repository.remove(item.product.id, item.amount)
        }
    }

    fun clear() {
        launch {
            repository.clear()
        }
    }

    fun checkout() {
        launch {
            val currentState = state.value
            if (currentState is BasketState.Success) {
                navigator.navigateToCheckout(currentState.items.map { it.item })
            }
        }
    }
}

data class BasketItemUiModel(
    val item: BasketItem,
    val model: BasketItemCardModel
)
