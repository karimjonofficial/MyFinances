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
    initialState = State.Initial(),
    logger = logger,
    coroutineScope = coroutineScope
) {
    val uiState = state.asStateFlow()

    fun initialize() = launch {
        if(state.value !is State.Loading<TLoading, List<TSuccess>, TFailure>)
            setStateLoading()
        setState(fetchState() ?: State.Failure(failure))
    }

    protected open suspend fun fetchState(): State.Success<TLoading, List<TSuccess>, TFailure>? {
        val response = repository.get()
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<TSuccess>): List<TSuccess> {
        return data
    }
}