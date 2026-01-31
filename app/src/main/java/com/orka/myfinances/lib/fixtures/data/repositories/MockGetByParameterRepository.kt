package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.GetByParameter
import kotlinx.coroutines.delay

interface MockGetByParameterRepository<T, P> : GetByParameter<T, P>, Repository {
    val items: MutableList<T>

    override suspend fun get(parameter: P): List<T>? {
        delay(duration)
        return items.filter(parameter)
    }

    suspend fun List<T>.filter(parameter: P): List<T>?
}