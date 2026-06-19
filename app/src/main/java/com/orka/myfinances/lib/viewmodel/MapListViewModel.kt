package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.Job

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
    private var query: String? = null
    private var fetchJob: Job? = null

    final override fun initialize() {
        fetch(query)
    }

    final override fun refresh() {
        fetch(query)
    }

    open fun search(query: String) {
        this.query = query.ifBlank { null }
        refresh()
    }

    private fun fetch(query: String?) {
        fetchJob?.cancel()
        fetchJob = launch {
            try {
                val oldState = state.value
                if (!(oldState is State.Loading && oldState.value != null)) {
                    setState(State.Loading(loading, oldState.value))
                }

                val response = get.getAll(query)
                if (response != null) {
                    setState(State.Success(map(response)))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}