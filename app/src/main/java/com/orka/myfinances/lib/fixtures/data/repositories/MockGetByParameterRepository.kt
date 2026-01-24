package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.GetByParameterRepository
import kotlinx.coroutines.delay

interface MockGetByParameterRepository<T, P> : GetByParameterRepository<T, P>, Repository {
    val items: MutableList<T>

    override suspend fun get(parameter: P): List<T>? {
        delay(duration)
        return items.filter(parameter)
    }

    suspend fun List<T>.filter(parameter: P): List<T>?
}