package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapperListViewModel<T, U>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: Get<T>,
    private val map: (T) -> U,
    logger: Logger
) : StateFul<State<List<U>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    final override fun initialize() {
        launch {
            try {
                val response = get.getAll()
                if(response != null) {
                    setState(State.Success(filterData(response).map { map(it) }))
                } else setState(State.Failure(failure))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        launch {
            try {
                val oldState = state.value
                val oldValue = oldState.value
                if(oldState !is State.Loading) {
                    setState(State.Loading(loading, oldValue))
                    val response = get.getAll()
                    if(response != null) {
                        setState(State.Success(filterData(response).map { map(it) }))
                    } else setState(State.Failure(failure))
                } else setState(State.Failure(UiText.Str("Refreshed when state was Loading")))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}