package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.data.models.Id

interface GetByIdRepository<T> {
    suspend fun getById(id: Id): T?
}