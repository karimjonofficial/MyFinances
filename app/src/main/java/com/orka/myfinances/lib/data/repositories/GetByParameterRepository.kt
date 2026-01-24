package com.orka.myfinances.lib.data.repositories

interface GetByParameterRepository<T, P> {
    suspend fun get(parameter: P): List<T>?
}