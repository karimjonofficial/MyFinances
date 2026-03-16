package com.orka.myfinances.lib.data.repositories

fun interface GetChunk<T> {
    suspend fun getChunk(size: Int, page: Int): List<T>?
}