package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetByIdRepository
import kotlinx.coroutines.delay

interface MockGetByIdRepository<T> : GetByIdRepository<T>, Repository {
    val items: MutableList<T>

    override suspend fun getById(id: Id): T? {
        delay(duration)
        return items.find(id)
    }

    suspend fun List<T>.find(id: Id): T?
}