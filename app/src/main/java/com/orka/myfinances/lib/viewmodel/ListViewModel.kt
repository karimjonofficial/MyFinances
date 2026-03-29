package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class ListViewModel<T>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: Get<T>,
    logger: Logger
) : StateFulViewModel<State<List<T>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    final override fun initialize() {
        launch {
            try {
                setState(fetchState() ?: State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                setState(fetchState() ?: State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    protected open suspend fun fetchState(): State.Success<List<T>>? {
        val response = get.getAll()
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}