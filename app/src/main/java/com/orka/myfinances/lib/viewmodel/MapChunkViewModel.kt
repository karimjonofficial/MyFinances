package com.orka.myfinances.lib.viewmodel

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import kotlinx.coroutines.Job
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
    private var fetchJob: Job? = null

    final override fun initialize() {
        fetch(1, query)
    }

    final override fun refresh() {
        resetPagination()
        fetch(index, query)
    }

    override fun search(query: String) {
        this.query = query.ifBlank { null }
        refresh()
    }

    override fun loadMore() {
        fetch(++index, query, append = true)
    }

    private fun fetch(page: Int, query: String?, append: Boolean = false) {
        fetchJob?.cancel()
        fetchJob = launch {
            try {
                val oldState = state.value
                if (!append && !(oldState is State.Loading && oldState.value != null)) {
                    setState(State.Loading(loading, oldState.value))
                } else if (append) {
                    setState(State.Loading(loading, if (oldState is State.Success) oldState.value else null))
                }

                val chunk = get.getChunk(size, page, query)
                if (chunk != null) {
                    val newData = if (append) {
                        val oldData = dataState.value
                        if (oldData != null) oldData + chunk.results else chunk.results
                    } else {
                        chunk.results
                    }
                    dataState.value = newData
                    val groupedData = map(chunk.copy(results = newData))
                    setState(State.Success(groupedData))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
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
                    UiText.Str("Refreshed when state was Loading"),
                    oldState.value
                )
            )
        }
    }

    protected fun resetPagination() {
        index = 1
    }
}