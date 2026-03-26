package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapListViewModel<T, K>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: Get<T>,
    private val map: (List<T>) -> Map<String, List<K>>,
    logger: Logger
) : SingleStateViewModel<State<Map<String, List<K>>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    override fun initialize() {
        launch {
            try {
                setState(State.Loading(loading))
                val data = get.getAll()
                if (data != null) {
                    val groupedData = map(data)
                    setState(State.Success(groupedData))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}