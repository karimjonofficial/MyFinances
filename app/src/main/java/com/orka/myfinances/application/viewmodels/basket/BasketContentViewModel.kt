package com.orka.myfinances.application.viewmodels.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.basket.BasketInteractor
import com.orka.myfinances.ui.screens.basket.BasketScreenModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BasketContentViewModel(
    private val repository: BasketRepository,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    loading: UiText,
    logger: Logger
) : SingleStateViewModel<State<BasketScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), BasketInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        repository.events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        val items = repository.get()
        val price = items.sumOf { it.product.salePrice * it.amount }
        setState(
            State.Success(
                value = BasketScreenModel(
                    items = items.map { item -> item.toUiModel(formatPrice, formatDecimal) },
                    price = formatPrice.formatPrice(price.toDouble())
                )
            )
        )
    }

    override fun increase(item: BasketItem) {
        launch {
            repository.add(id = Id(item.product.id), amount = 1)
        }
    }

    override fun decrease(item: BasketItem) {
        launch {
            repository.remove(Id(item.product.id), 1)
        }
    }

    override fun remove(item: BasketItem) {
        launch {
            repository.remove(Id(item.product.id), item.amount)
        }
    }

    override fun clear() {
        launch {
            repository.clear()
        }
    }

    override fun checkout() {
        launch {
            val successState = (state.value as? State.Success)?.value
            if (successState != null) {
                navigator.navigateToCheckout(successState.items.map { it.item })
            }
        }
    }
}