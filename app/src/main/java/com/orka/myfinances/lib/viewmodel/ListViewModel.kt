package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class ListViewModel<T>(
    loading: UiText,
    private val failure: UiText,
    private val repository: Get<T>,
    logger: Logger
) : SingleStateViewModel<State<List<T>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    override fun initialize() {
        launch {
            try {
                setState(fetchState() ?: State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    protected open suspend fun fetchState(): State.Success<List<T>>? {
        val response = repository.getAll()
        return if(response != null) State.Success(filterData(response)) else null
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}