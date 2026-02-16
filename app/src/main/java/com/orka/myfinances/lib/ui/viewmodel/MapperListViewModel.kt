package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.list.State

abstract class MapperListViewModel<T, U>(
    private val loading: UiText,
    private val failure: UiText,
    private val repository: Get<T>,
    private val map: (T) -> U,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    override fun initialize() {
        launch {
            if(state.value !is State.Loading)
                setStateLoading()
            setState(fetchState() ?: State.Failure(failure))
        }
    }

    protected open suspend fun fetchState(): State.Success<List<U>>? {
        val response = repository.get()
        return if(response != null) State.Success(filterData(response).map { map(it) }) else null
    }

    protected open fun setStateLoading() {
        setState(State.Loading(loading))
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}