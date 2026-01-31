package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.Get
import kotlinx.coroutines.delay

interface MockGetRepository<T> : Get<T>, Repository {
    val items: MutableList<T>

    override suspend fun get(): List<T>? {
        delay(duration)
        return items.toList()
    }
}