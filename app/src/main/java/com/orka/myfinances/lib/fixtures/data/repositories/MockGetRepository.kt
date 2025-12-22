package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.lib.data.repositories.GetRepository
import kotlinx.coroutines.delay

abstract class MockGetRepository<T>(
    items: List<T>,
    protected val duration: Long = 1000
) : GetRepository<T> {
    protected val items = items.toMutableList()

    override suspend fun get(): List<T>? {
        delay(duration)
        return items.toList()
    }
}