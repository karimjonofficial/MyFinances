package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.lib.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow

abstract class ListViewModel<TLoading, TSuccess, TFailure>(
    private val loading: TLoading,
    private val failure: TFailure,
    private val repository: Repository<TSuccess>,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ViewModel<State<TLoading, List<TSuccess>, TFailure>>(
    initialState = State.initial(),
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        setStateLoading()
        setState(fetchState() ?: State.failure(failure))
    }

    protected open suspend fun fetchState(): State.Success<TLoading, List<TSuccess>, TFailure>? {
        val response = repository.get()
        return if(response != null) State.success(filterData(response)) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<TSuccess>): List<TSuccess> {
        return data
    }
}