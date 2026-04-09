package com.orka.myfinances.application.viewmodels.basket

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.repositories.basket.BasketRepository
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
        val items = repository.get()
        val price = items.sumOf { it.product.salePrice * it.amount }
        State.Success(
            value = BasketScreenModel(
                items = items.map { item -> item.toUiModel(formatPrice, formatDecimal) },
                price = formatPrice.formatPrice(price.toDouble())
            )
        )
    },
    logger = logger
), BasketInteractor {
    val uiState = state.asStateFlow()

    init {
        repository.events.onEach {
            initialize()
        }.launchIn(viewModelScope)
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