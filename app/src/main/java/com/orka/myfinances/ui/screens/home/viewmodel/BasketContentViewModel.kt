package com.orka.myfinances.ui.screens.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BasketContentViewModel(
    private val repository: BasketRepository,
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
        setState(BasketState.Success(items, price))
    }

    fun increase(id: Id) {
        launch {
            repository.add(id = id, amount = 1)
        }
    }

    fun decrease(id: Id) {
        launch {
            repository.remove(id, 1)
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
}