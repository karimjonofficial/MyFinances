package com.orka.myfinances.lib.data.repositories

interface AddRepository<T, R> {
    suspend fun add(request: R): T?
}