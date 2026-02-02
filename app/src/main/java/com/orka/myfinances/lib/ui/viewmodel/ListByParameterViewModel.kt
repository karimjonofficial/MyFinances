package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.lib.data.repositories.GetByParameter
import kotlinx.coroutines.flow.asStateFlow

abstract class ListByParameterViewModel<L, S, F, P>(
    private val parameter: P,
    private val loading: L,
    private val failure: F,
    private val repository: GetByParameter<S, P>,
    logger: Logger
) : SingleStateViewModel<State<L, List<S>, F>>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            if(state.value !is State.Loading<L>)
                setStateLoading()
            setState(fetchState() ?: State.Failure(failure))
        }
    }

    protected open suspend fun fetchState(): State.Success<List<S>>? {
        val response = repository.get(parameter)
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<S>): List<S> {
        return data
    }
}