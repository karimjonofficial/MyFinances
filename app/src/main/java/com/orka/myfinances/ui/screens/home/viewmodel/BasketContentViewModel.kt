package com.orka.myfinances.ui.screens.home.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketEvent
import com.orka.myfinances.data.repositories.basket.BasketRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

//TODO solve concurrency problems
class BasketContentViewModel(
    private val repository: BasketRepository,
    logger: Logger
) : ViewModel<BasketState>(
    initialState = BasketState.Loading,
    logger = logger
) {
    private var job: Job? = null
    val uiState = state.asStateFlow()

    private fun setJob(job: Job) {
        val j = this.job
        if (j == null)
            this.job = job
        else {
            this.job = launch {
                j.invokeOnCompletion {  }
                this.job = job
            }
        }
    }

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
        setJob(initializeJob())
    }

    fun clear() {
        setJob(clearJob())
    }

    fun increase(id: Id) {
        setJob(increaseJob(id))
    }

    fun decrease(id: Id) {
        setJob(decreaseJob(id))
    }

    fun remove(item: BasketItem) {
        setJob(removeJob(item))
    }

    private fun increaseJob(id: Id) = launch {
        repository.add(id = id, amount = 1)
        initialize()
    }
    private fun decreaseJob(id: Id) = launch {
        repository.remove(id, 1)
        initialize()
    }
    private fun removeJob(item: BasketItem) = launch {
        repository.remove(item.product.id, item.amount)
        initialize()
    }
    private fun clearJob() = launch {
        repository.clear()
    }
    private fun initializeJob() = launch {
        val items = repository.get()
        val price = items.sumOf { it.product.salePrice * it.amount }
        setState(BasketState.Success(items, price))
    }
}