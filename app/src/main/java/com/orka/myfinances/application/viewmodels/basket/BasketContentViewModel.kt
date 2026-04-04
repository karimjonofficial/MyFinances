package com.orka.myfinances.application.viewmodels.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFulViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.basket.BasketInteractor
import com.orka.myfinances.ui.screens.basket.BasketItemUiModel
import com.orka.myfinances.ui.screens.basket.BasketScreenModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BasketContentViewModel(
    private val repository: BasketRepository,
    private val navigator: Navigator,
    private val formatPrice: FormatPrice,
    private val formatDecimal: FormatDecimal,
    private val loading: UiText,
    logger: Logger
) : StateFulViewModel<State<BasketScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), BasketInteractor {
    val uiState = state.asStateFlow()

    init {
        repository.events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
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
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
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
            val successState = (state.value as? State.Success)?.value
            if (successState != null) {
                navigator.navigateToCheckout()
            }
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val items = repository.get()
                val price = items.sumOf { it.product.salePrice * it.amount }
                setState(
                    State.Success(
                        value = BasketScreenModel(
                            items = items.map { item ->
                                item.toUiModel(
                                    formatPrice,
                                    formatDecimal
                                )
                            },
                            price = formatPrice.formatPrice(price.toDouble())
                        )
                    )
                )
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}