package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.list.State
import kotlinx.coroutines.flow.asStateFlow

abstract class ListByParameterViewModel<T, P>(
    private val parameter: P,
    private val loading: UiText,
    private val failure: UiText,
    private val repository: GetByParameter<T, P>,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            if(state.value !is State.Loading)
                setStateLoading()
            setState(fetchState() ?: State.Failure(failure))
        }
    }

    protected open suspend fun fetchState(): State.Success<List<T>>? {
        val response = repository.get(parameter)
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}