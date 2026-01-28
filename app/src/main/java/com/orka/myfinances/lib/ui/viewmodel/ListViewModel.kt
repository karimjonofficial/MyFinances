package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.lib.data.repositories.GetRepository
import kotlinx.coroutines.flow.asStateFlow

abstract class ListViewModel<TLoading, TSuccess, TFailure>(
    private val loading: TLoading,
    private val failure: TFailure,
    private val repository: GetRepository<TSuccess>,
    logger: Logger
) : SingleStateViewModel<State<TLoading, List<TSuccess>, TFailure>>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            if(state.value !is State.Loading<TLoading>)
                setStateLoading()
            setState(fetchState() ?: State.Failure(failure))
        }
    }

    protected open suspend fun fetchState(): State.Success<List<TSuccess>>? {
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