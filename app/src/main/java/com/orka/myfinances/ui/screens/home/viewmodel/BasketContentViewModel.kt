package com.orka.myfinances.ui.screens.home.viewmodel

import android.util.Log
import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class BasketContentViewModel(
    private val repository: BasketRepository,
    logger: Logger
) : ViewModel<BasketState>(
    initialState = BasketState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        launch {
            repository.events.collectLatest { event ->
                when (event) {
                    is BasketEvent.Clear -> initialize()
                }
            }
        }
    }

    fun initialize() {
        Log.d("BasketContentViewModel", "initialize:")
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