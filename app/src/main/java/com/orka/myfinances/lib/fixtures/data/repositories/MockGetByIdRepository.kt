package com.orka.myfinances.lib.fixtures.data.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import kotlinx.coroutines.delay

interface MockGetByIdRepository<T> : GetById<T>, Repository {
    val items: MutableList<T>

    override suspend fun getById(id: Id): T? {
        delay(duration)
        return items.find(id)
    }

    suspend fun List<T>.find(id: Id): T?
}