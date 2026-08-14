package com.orka.myfinances.lib.viewmodel.sourceful.chunk

import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.viewmodel.Paginated
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.statuses.failure.ExecutedFromFailure
import com.orka.myfinances.ui.statuses.failure.LoadedAbsentPage
import com.orka.myfinances.ui.statuses.loading.LoadMore

abstract class MapChunkViewModel<TData, TUi>(
    private val get: GetChunk<TData>,
    protected val map: suspend (TData) -> TUi,
    protected val groupBy: (TData) -> String,
    exceptionMapper: ExceptionMapper<ChunkUiModel<TUi>> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<Chunk<TData>, ChunkUiModel<TUi>>(
    loadData = { get.getChunk(10, 1) },
    map = {
        val map = it.results
            .groupBy(groupBy)
            .mapValues { entry -> entry.value.map { value -> map(value) } }
        ChunkUiModel(
            size = 10,
            pageIndex = it.pageIndex,
            nextPageIndex = it.nextPageIndex,
            previousPageIndex = it.previousPageIndex,
            content = map
        )
    },
    exceptionMapper = exceptionMapper,
    logger = logger
), Paginated {
    final override fun loadMore() {
        tryTransition(loadingState = { oldState ->
            State.Loading(
                status = LoadMore,
                value = oldState.value
            )
        }) { oldState ->
            if (oldState is State.Success) {
                val size = 10
                val index = oldState.value.pageIndex
                val oldMap = oldState.value.content
                val newPage = index + 1
                val chunk = get.getChunk(size, newPage)
                if (chunk != null) {
                    val newMap = chunk.results.toUiMap()
                    val newContent = oldMap.merge(newMap)

                    State.Success(
                        value = ChunkUiModel(
                            size = size,
                            pageIndex = newPage,
                            nextPageIndex = chunk.nextPageIndex,
                            previousPageIndex = chunk.previousPageIndex,
                            content = newContent
                        )
                    )
                } else State.Failure(status = LoadedAbsentPage, value = oldState.value)
            } else State.Failure(status = ExecutedFromFailure, value = oldState.value)
        }
    }

    protected suspend fun List<TData>.toUiMap(): Map<String, List<TUi>> {
        return groupBy(groupBy).mapValues { entry -> entry.value.map { map(it) } }
    }

    protected suspend fun Chunk<TData>.toUiModel(): ChunkUiModel<TUi> {
        return ChunkUiModel(
            size = 10,
            pageIndex = pageIndex,
            nextPageIndex = nextPageIndex,
            previousPageIndex = previousPageIndex,
            content = results.toUiMap()
        )
    }

    protected fun <K, V> Map<K, List<V>>.merge(
        other: Map<K, List<V>>
    ): Map<K, List<V>> =
        (this.keys + other.keys).associateWith { key ->
            (this[key].orEmpty() + other[key].orEmpty())
        }
}
