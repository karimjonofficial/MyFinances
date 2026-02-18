package com.orka.myfinances.lib.ui.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.list.State

abstract class MapViewModel<T, K>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: Get<T>,
    private val map: (List<T>) -> Map<String, List<K>>,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            val data = get.get()
            if (data != null) {
                val groupedData = map(data)
                setState(State.Success(groupedData))
            } else {
                setState(State.Failure(failure))
            }
        }
    }
}