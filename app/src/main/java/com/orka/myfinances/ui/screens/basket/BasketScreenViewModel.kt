package com.orka.myfinances.ui.screens.basket

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.basket.BasketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.yield

class BasketScreenViewModel(
    private val repository: BasketRepository,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<BasketScreenState>(
    initialState = BasketScreenState.Loading,
    logger = logger,
    coroutineScope = coroutineScope
    ) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        val items = repository.get()
        val price = items.sumOf { it.product.price * it.amount }
        setState(BasketScreenState.Success(items, price))
    }

    fun increase(id: Id) = launch {
        repository.add(id, 1)
        yield()
        initialize()
    }
}