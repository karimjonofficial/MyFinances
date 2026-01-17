package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetByIdRepository
import kotlinx.coroutines.delay

abstract class MockGetByIdRepository<T>(
    protected val items: List<T>,
    protected val duration: Long = 1000
) : GetByIdRepository<T> {

    override suspend fun getById(id: Id): T? {
        delay(duration)
        return items.find { find(id, it) }
    }

    protected abstract fun find(id: Id, item: T): Boolean
}