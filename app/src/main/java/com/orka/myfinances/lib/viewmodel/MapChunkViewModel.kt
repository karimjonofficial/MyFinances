package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MapChunkViewModel<T, K>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: GetChunk<T>,
    private val map: (List<T>) -> Map<String, List<K>>,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    protected val dataState = MutableStateFlow<List<T>?>(null)
    protected val size = 10
    protected var index = 1

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            resetPagination()
            val data = get.getChunk(size, 1)
            if (data != null) {
                val groupedData = map(data)
                setState(State.Success(groupedData))
            } else setState(State.Failure(failure))
        }
    }

    fun loadMore() {
        launch {
            val oldState = state.value
            val oldData = dataState.value
            if(oldState is State.Success<*> && oldData != null) {
                setState(State.Loading(loading))
                val data = get.getChunk(size, ++index)
                if (data != null) {
                    val groupedData = map(data + oldData)
                    setState(State.Success(groupedData))
                } else setState(State.Failure(failure))
            } else setState(State.Failure(failure))
        }
    }

    protected fun resetPagination() {
        index = 1
    }
}