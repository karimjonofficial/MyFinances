package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.Id

fun interface CompleteOrder {
    suspend fun complete(id: Id): Boolean
}
