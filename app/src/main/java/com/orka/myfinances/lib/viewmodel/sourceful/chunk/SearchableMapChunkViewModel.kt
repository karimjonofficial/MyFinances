package com.orka.myfinances.lib.viewmodel.sourceful.chunk

import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.viewmodel.Paginated
import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.statuses.failure.ExecutedFromFailure
import com.orka.myfinances.ui.statuses.failure.LoadedAbsentPage
import com.orka.myfinances.ui.statuses.failure.CalledSearchMoreWithNullQuery
import com.orka.myfinances.ui.statuses.loading.Search
import com.orka.myfinances.ui.statuses.loading.ResetSearch
import com.orka.myfinances.ui.statuses.loading.LoadMore
import kotlinx.coroutines.flow.MutableStateFlow

abstract class SearchableMapChunkViewModel<TData, TUi>(
    get: GetChunk<TData>,
    private val searchRepository: SearchChunk<TData>,
    map: suspend (Chunk<TData>) -> ChunkUiModel<TUi>,
    exceptionMapper: ExceptionMapper<ChunkUiModel<TUi>> = NetworkExceptionMapper(),
    logger: Logger
) : MapChunkViewModel<TData, TUi>(
    get = get,
    map = map,
    exceptionMapper = exceptionMapper,
    logger = logger
), Paginated, PaginatedSearchable {
    private val queryState: MutableStateFlow<String?> = MutableStateFlow(null)

    final override fun search(query: String) {
        tryTransition(
            loadingState = { oldState -> State.Loading(status = Search, value = oldState.value) }
        ) { oldState ->
            if(query.isNotEmpty()) {
                val chunk = searchRepository.searchChunk(10, 1, query)
                if (chunk != null) {
                    val map = map(chunk)
                    queryState.value = query
                    State.Success(value = map)
                } else State.Failure(status = LoadedAbsentPage, value = oldState.value)
            } else produceInitialState()
        }
    }

    final override fun searchMore() {
        tryTransition(loadingState = { oldState -> State.Loading(status = LoadMore, value = oldState.value) }) { oldState ->
            if(oldState is State.Success) {
                if (queryState.value != null) {
                    val index = oldState.value.pageIndex
                    val chunk = searchRepository.searchChunk(10, index + 1, queryState.value!!)
                    if (chunk != null) {
                        val map = map(chunk)
                        val oldMap = oldState.value.content
                        val newValue = map.copy(
                            content = oldMap + map.content
                        )
                        State.Success(value = newValue)
                    } else State.Failure(status = LoadedAbsentPage, value = oldState.value)
                } else State.Failure(status = CalledSearchMoreWithNullQuery, value = oldState.value)
            } else State.Failure(status = ExecutedFromFailure, value = oldState.value)
        }
    }

    final override fun resetSearch() {
        tryTransition(loadingState = { oldState -> State.Loading(ResetSearch, oldState.value)}) { oldState ->
            if(oldState is State.Success) {
                val state = produceInitialState()
                if(state is State.Success)
                    queryState.value = null
                state
            } else State.Failure(status = ExecutedFromFailure, value = oldState.value)
        }
    }
}