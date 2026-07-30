package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.data.repositories.Chunk

fun interface GetChunk<T> {
    suspend fun getChunk(size: Int, page: Int): Chunk<T>?
}