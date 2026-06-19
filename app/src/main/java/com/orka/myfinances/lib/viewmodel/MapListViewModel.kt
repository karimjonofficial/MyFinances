package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State

abstract class MapListViewModel<T, K>(
    protected val loading: UiText,
    protected val failure: UiText,
    private val get: Get<T>,
    private val map: (List<T>) -> Map<String, List<K>>,
    logger: Logger
) : StateFulViewModel<State<Map<String, List<K>>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    protected var query: String? = null

    final override fun initialize() {
        launch {
            try {
                val response = get.getAll(null)
                if (response != null) {
                    setState(State.Success(map(response)))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        tryTransition { oldState ->
            val response = get.getAll(query)
            if (response != null) {
                State.Success(map(response))
            } else State.Failure(failure, oldState.value)
        }
    }

    open fun search(query: String) {
        this.query = query.ifBlank { null }
        refresh()
    }

    protected fun tryTransition(produceState: suspend (State<Map<String, List<K>>>) -> State<Map<String, List<K>>>) {
        launch {
            val oldState = state.value
            if (oldState !is State.Loading) {
                try {
                    setState(State.Loading(loading, oldState.value))
                    val newState = produceState(oldState)
                    setState(newState)
                } catch (e: Exception) {
                    setState(State.Failure(UiText.Str(e.message.toString()), oldState.value))
                }
            } else setState(
                State.Failure(
                    error = UiText.Str("Action when state was Loading"),
                    oldState.value
                )
            )
        }
    }
}
