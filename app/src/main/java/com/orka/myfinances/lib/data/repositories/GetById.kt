package com.orka.myfinances.lib.data.repositories

import com.orka.myfinances.data.models.Id

fun interface GetById<T> {
    suspend fun getById(id: Id): T?
}