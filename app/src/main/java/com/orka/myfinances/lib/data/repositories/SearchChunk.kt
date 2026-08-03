package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.data.repositories.Chunk

fun interface SearchChunk<T> {
    suspend fun searchChunk(size: Int, page: Int, query: String?): Chunk<T>?
}
