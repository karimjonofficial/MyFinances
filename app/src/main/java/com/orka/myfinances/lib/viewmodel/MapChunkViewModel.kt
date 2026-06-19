package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.flow.MutableStateFlow

abstract class MapChunkViewModel<TApi, TUi>(
    protected val loading: UiText,
    protected val failure: UiText,
    private val get: GetChunk<TApi>,
    private val map: suspend (Chunk<TApi>) -> ChunkUiModel<TUi>,
    logger: Logger
) : StateFulViewModel<State<ChunkUiModel<TUi>>>(
    initialState = State.Loading(loading),
    logger = logger
), ChunkViewModel {
    protected val dataState = MutableStateFlow<List<TApi>?>(null)
    protected val size = 10
    protected var index = 1
    protected var query: String? = null

    final override fun initialize() {
        launch {
            try {
                val chunk = get.getChunk(size, 1, null)
                if (chunk != null) {
                    dataState.value = chunk.results
                    val groupedData = map(chunk)
                    setState(State.Success(groupedData))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    final override fun refresh() {
        tryTransition { oldState ->
            val chunk = get.getChunk(size, 1, query)
            resetPagination()
            if (chunk != null) {
                dataState.value = chunk.results
                val groupedData = map(chunk)
                State.Success(groupedData)
            } else State.Failure(failure, oldState.value)
        }
    }

    override fun loadMore() {
        tryTransition { oldState ->
            if(oldState !is State.Success)
                State.Failure(failure, oldState.value)
            else {
                val oldData = dataState.value
                val chunk = get.getChunk(size, ++index, query)

                if (chunk == null)
                    State.Failure(failure, oldState.value)
                else {
                    val newData = if (oldData != null) oldData + chunk.results else chunk.results
                    dataState.value = newData
                    val groupedData = map(chunk.copy(results = newData))
                    State.Success(groupedData)
                }
            }
        }
    }

    override fun search(query: String) {
        logger.log(this.javaClass.name, "Search query: $query")
        this.query = query.ifBlank { null }
        refresh()
    }

    protected fun tryTransition(produceState: suspend (State<ChunkUiModel<TUi>>) -> State<ChunkUiModel<TUi>>) {
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

    protected fun resetPagination() {
        index = 1
    }
}
