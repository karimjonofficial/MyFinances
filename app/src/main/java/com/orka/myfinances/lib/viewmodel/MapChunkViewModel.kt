package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MapChunkViewModel<TApi, TUi>(
    private val loading: UiText,
    private val failure: UiText,
    private val get: GetChunk<TApi>,
    private val map: (Chunk<TApi>) -> ChunkMapState<TUi>,
    logger: Logger
) : StateFulViewModel<State<ChunkMapState<TUi>>>(
    initialState = State.Loading(loading),
    logger = logger
) {
    protected val dataState = MutableStateFlow<List<TApi>?>(null)
    protected val size = 10
    protected var index = 1

    final override fun initialize() {
        launch {
            try {
                val chunk = get.getChunk(size, 1)
                if (chunk != null) {
                    dataState.value = chunk.results
                    val groupedData = map(chunk)
                    setState(State.Success(groupedData))
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
                resetPagination()
                if(!(oldState is State.Loading && oldState.value != null)) {
                    setState(State.Loading(loading))
                }
                val chunk = get.getChunk(size, index)
                if (chunk != null) {
                    dataState.value = chunk.results
                    val groupedData = map(chunk)
                    setState(State.Success(groupedData))
                } else setState(State.Failure(failure))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    fun loadMore() {
        launch {
            try {
                val oldState = state.value
                setState(
                    State.Loading(
                        message = loading,
                        value = if(oldState is State.Success) oldState.value else null
                    )
                )
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
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    protected fun resetPagination() {
        index = 1
    }
}