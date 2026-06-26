package com.orka.myfinances.lib.data.models

import com.orka.myfinances.lib.viewmodel.Chunk

fun <T, R> ChunkApiModel<T>.toChunk(map: (T) -> R): Chunk<R> {
    return Chunk(
        count = count,
        pageIndex = pageIndex,
        nextPageIndex = nextPageIndex,
        previousPageIndex = previousPageIndex,
        results = results.map(map)
    )
}
