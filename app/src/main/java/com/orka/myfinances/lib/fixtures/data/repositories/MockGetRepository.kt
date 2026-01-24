package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.GetRepository
import kotlinx.coroutines.delay

interface MockGetRepository<T> : GetRepository<T>, Repository {
    val items: MutableList<T>

    override suspend fun get(): List<T>? {
        delay(duration)
        return items.toList()
    }
}