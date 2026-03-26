package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MapChunkViewModel<T, K>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: GetChunk<T>,
    private val map: (Chunk<T>) -> ChunkMapState<K>,
    logger: Logger
) : SingleStateViewModel<State<ChunkMapState<K>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    protected val dataState = MutableStateFlow<List<T>?>(null)
    protected val size = 10
    protected var index = 1

    override fun initialize() {
        launch {
            val oldState = state.value
            if (!(oldState is State.Loading && oldState.value != null)) {
                setState(State.Loading(loading))
                resetPagination()
            }
            val chunk = get.getChunk(size, 1)
            if (chunk != null) {
                dataState.value = chunk.results
                val groupedData = map(chunk)
                setState(State.Success(groupedData))
            } else setState(State.Failure(failure))
        }
    }

    fun loadMore() {
        launch {
            val oldState = state.value
            setState(State.Loading(loading, if(oldState is State.Success) oldState.value else null))
            val oldData = dataState.value
            val chunk = get.getChunk(size, ++index)
            if (chunk == null || oldState !is State.Success)
                setState(State.Failure(failure))
            else {
                val newData = if (oldData != null) oldData + chunk.results else chunk.results
                dataState.value = newData
                val groupedData = map(chunk.copy(results = newData))
                setState(State.Success(groupedData))
            }
        }
    }

    protected fun resetPagination() {
        index = 1
    }
}