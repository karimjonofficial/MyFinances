package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.lib.viewmodel.Chunk

fun interface GetChunk<T> {
    suspend fun getChunk(size: Int, page: Int): Chunk<T>?
}