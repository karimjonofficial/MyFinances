package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.Text
import kotlinx.coroutines.flow.asStateFlow

abstract class ListViewModel<T>(
    private val loading: Text,
    private val failure: Text,
    private val repository: Get<T>,
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
        val response = repository.get()
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}