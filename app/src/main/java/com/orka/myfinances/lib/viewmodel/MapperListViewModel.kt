package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapperListViewModel<T, U>(
    loading: UiText,
    private val failure: UiText,
    private val get: Get<T>,
    private val map: (T) -> U,
    logger: Logger
) : SingleStateViewModel<State<List<U>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    override fun initialize() {
        launch {
            try {
                setState(fetchState() ?: State.Failure(failure))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    protected open suspend fun fetchState(): State.Success<List<U>>? {
        val response = get.getAll()
        return if(response != null) State.Success(filterData(response).map { map(it) }) else null
    }

    protected open fun filterData(data: List<T>): List<T> {
        return data
    }
}