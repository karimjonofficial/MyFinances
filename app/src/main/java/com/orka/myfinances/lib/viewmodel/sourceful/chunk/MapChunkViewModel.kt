package com.orka.myfinances.lib.viewmodel.sourceful.chunk

import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.viewmodel.base.ExceptionMapper
import com.orka.myfinances.lib.viewmodel.mappers.NetworkExceptionMapper
import com.orka.myfinances.lib.viewmodel.sourceful.SourceFulViewModel
import com.orka.myfinances.logger.Logger

abstract class MapChunkViewModel<TData, TUi>(
    private val get: GetChunk<TData>,
    private val map: suspend (Chunk<TData>) -> ChunkUiModel<TUi>,
    exceptionMapper: ExceptionMapper<ChunkUiModel<TUi>> = NetworkExceptionMapper(),
    logger: Logger
) : SourceFulViewModel<Chunk<TData>, ChunkUiModel<TUi>>(
    loadData = { get.getChunk(10, 1) },
    map = map,
    exceptionMapper = exceptionMapper,
    logger = logger
), ChunkViewModel {
    override fun loadMore() {
        tryTransition { oldState ->
            if(oldState is State.Success) {
                val index = oldState.value.pageIndex
                val size = oldState.value.size
                val oldContent = oldState.value.content
                val chunk = get.getChunk(size, index + 1)
                if(chunk != null) {
                    val newContent = oldContent + map(chunk).content

                    State.Success(
                        value = ChunkUiModel(
                            size = size,
                            pageIndex = index + 1,
                            nextPageIndex = chunk.nextPageIndex,
                            previousPageIndex = chunk.previousPageIndex,
                            content = newContent
                        )
                    )
                } else State.Failure(type = LoadedAbsentPage, value = oldState.value)
            } else State.Failure(type = ExecutedFromFailure, value = oldState.value)
        }
    }
}