package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.yield

class BasketContentViewModel(
    private val repository: BasketRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<BasketState>(
    initialState = BasketState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val items = repository.get()
        val price = items.sumOf { it.product.price * it.amount }
        setState(BasketState.Success(items, price))
    }

    fun clear() = launch {
        repository.clear()
        initialize()
    }

    fun increase(id: Id) = launch {
        repository.add(id, 1)
        yield()
        initialize()
    }

    fun decrease(id: Id) = launch {
        repository.remove(id, 1)
        yield()
        initialize()
    }
}