package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Id

fun interface SetPaid {
    suspend fun setPaid(id: Id): Boolean
}
