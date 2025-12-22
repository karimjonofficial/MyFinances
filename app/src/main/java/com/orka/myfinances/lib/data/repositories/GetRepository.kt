package com.orka.myfinances.lib.data.repositories

interface GetRepository<T> {
    suspend fun get(): List<T>?
}