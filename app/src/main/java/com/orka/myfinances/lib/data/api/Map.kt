package com.orka.myfinances.lib.data.api

import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk

fun <T> ChunkApiModel<T>.map(): Chunk<T> {
    return Chunk(
        count = count,
        pageIndex = pageIndex,
        nextPageIndex = nextPageIndex,
        previousPageIndex = previousPageIndex,
        results = results
    )
}